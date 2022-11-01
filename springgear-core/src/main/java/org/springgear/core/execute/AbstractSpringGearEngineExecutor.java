package org.springgear.core.execute;

//import com.google.common.eventbus.AsyncEventBus;
import org.springgear.core.context.EmptyRequest;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.context.SpringGearResultOriginalWrapper;
import org.springgear.core.context.SpringGearResultWrapper;
import org.springgear.core.handler.SpringGearHandler;
//import org.springgear.eventbus.SpringGearEventListener;
//import org.springgear.eventbus.annotation.SpringGearEvent;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearInterruptException;
import org.springgear.exception.SpringGearException;
import org.springgear.support.constants.HttpStatus;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * spring gear 的抽象实现
 *
 * @author SHUAI.W
 * @date 2017-12-13
 **/
@Slf4j
public abstract class AbstractSpringGearEngineExecutor implements SpringGearEngineInterface, BeanNameAware, InitializingBean {

    @Setter
    private String beanName;

    /**
     * 使用的 handlers
     */
    @Setter
    private List<SpringGearHandler<?, ?>> handlers;

    /**
     * 要注册的监听器
     */
//    @Setter
//    private List<SpringGearEventListener> listeners;

    @Setter
    private SpringGearResultWrapper wrapper;

    /**
     * eventbus
     */
//    private AsyncEventBus eventBus = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 将处理流程进行扩展的附加输出，不再进行耦合
        this.initResultWrapper();
//        this.initEventBus();
    }

    /**
     * 初始化 result wrapper
     */
    private void initResultWrapper() {
        if (wrapper != null) {
            return;
        }
        wrapper = new SpringGearResultOriginalWrapper();
    }

    /**
     * 初始化 eventBus
     */
//    private void initEventBus() {
//        if (CollectionUtils.isEmpty(listeners)) {
//            return;
//        }
//
//        eventBus = new AsyncEventBus(beanName, Executors.newFixedThreadPool(5));
//        listeners.forEach(eventBus::register);
//    }

    @Override
    public Object execute(Object request, long timestamp, Object... others) throws SpringGearException {
        if (log.isDebugEnabled()) {
            log.debug("Springgear core for bean `{}` start execute main process with handlers: {}", this.beanName, this.handlers);
        }
        if (CollectionUtils.isEmpty(handlers)) {
            log.warn("There is no handlers ware injected to bean `{}`, please check.", this.beanName);
            return null;
        }

        if (request == null) {
            request = EmptyRequest.INSTANCE;
        }

        String source = this.getSource();
        SpringGearContext context = new SpringGearContext(request, source, timestamp);

        if (log.isDebugEnabled()) { // 入参记录，方便问题跟踪，只记录一次就好。
            log.debug("TS[{}-{}] Handler '{}' start work. request is: {}", source, timestamp, beanName, request);
        }

        /**
         * 核心 handlers 循环处理
         */
        for (SpringGearHandler<?, ?> handler : handlers) {

            if (!handler.supports(context, others)) { // 如果不支持，则 continue。
                continue;
            }

            // event 获取
//            SpringGearEvent sge = handler.getClass().getAnnotation(SpringGearEvent.class);
            Throwable ex = null;
            try {
                handler.handle(context, others);
            } catch (Throwable e) { // 捕获并处理异常
                ex = e;
                this.handleThrowable(context, e);
            } finally {
                long duration = System.currentTimeMillis() - timestamp;

                if (log.isDebugEnabled()) {
                    log.debug("TS[{}-{}] Handler '{}#{}' finished work. duration {} ms. context is: {}",
                            source, timestamp, handler.getClass().getSimpleName(), null, duration, context);
//                            source, timestamp, handler.getClass().getSimpleName(), handler.getOrder(), duration, context);
                }

//                if (eventBus != null && sge != null) {
//                    if (sge.copied()) {
//                        // TODO
//                        //clone 对象，防止地址变更对数据的影响
//                    }
//                    log.debug("TS[{}-{}] Handler '{}#{}' has event, do post: {}", source, timestamp, handler.getClass().getSimpleName(), handler.getOrder(), context);
//                    eventBus.post(new Object[]{
//                            context, ex, handler.getClass()
//                    });
//                }

            }

        }

        long duration = System.currentTimeMillis() - timestamp;

        // 记录一次 context，方便线上问题排查
        if (log.isInfoEnabled()) {
            log.info("TS[{}-{}] Handler '{}' finished work. duration {} ms. context is: {}", source, timestamp, beanName, duration, context);
        }

        final Object response = context.getResponse();
        return response;
    }

    @Override
    public Object wrap(Object req, Object resp, long timestamp, int code, String msg, Object... others) {

        if (log.isDebugEnabled()) {
            log.debug("start wrap parameters, req: `{}`, resp: `{}`, timestamp: `{}`, code: `{}`, msg: `{}`", req, resp, timestamp, code, msg);
        }

        return wrapper.process(req, resp, timestamp, code, msg, others);
    }

    /**
     * 针对各种异常的处理。
     * TODO 将异常及对应的 code 做成 mapping 形式，进行自动化。
     *
     * @param context
     * @param e
     * @throws SpringGearException
     */
    private void handleThrowable(SpringGearContext<?, ?> context, Throwable e) throws SpringGearException {
        String source = context.getSource();
        long timestamp = context.getTimestamp();
        Object response = context.getResponse();
        // 打印堆栈
        String localizedMessage = e.getLocalizedMessage();

        // TODO
        String logMessage = String.format("TS[%s-%s] %s：%s | %s | STACK TRACE: ",
                source, timestamp, e.getClass().getSimpleName(), localizedMessage, context);

        if (!(e instanceof SpringGearException)) {
            log.error(logMessage, e);
            int status = HttpStatus.SC_INTERNAL_SERVER_ERROR;

            if (e instanceof IllegalArgumentException) { // 参数一场，标记 status
                status = HttpStatus.SC_BAD_REQUEST;
                // TODO
//            } else if (e instanceof RpcException) { // RPC 调用一场，标记 status
//                status = HttpStatus.SC_BAD_GATEWAY;
//                localizedMessage = "系统异常，请重试或联系客服[JSF]";
            } else if (e instanceof NullPointerException) { // 空指针异常，给一个友好文案。
                localizedMessage = "系统异常，请重试或联系客服[N]";
            } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) { // 超时异常
                localizedMessage = "系统异常，请重试或联系客服[T]";
            } else {
                localizedMessage = "系统异常，请重试或联系客服[Z]";
            }
            // 异常抛出
            throw new SpringGearException(localizedMessage, status, timestamp);
        }

        ((SpringGearException) e).setTimestamp(timestamp);

        if (e instanceof SpringGearContinueException) {// 捕获到 continue 异常，返回继续向下执行代码。
            log.warn(logMessage);
//            log.warn(logMessage, e);
            return;
        } else if (e instanceof SpringGearInterruptException) {
            switch (((SpringGearInterruptException) e).getCode()) { // 以下几种类型不记录堆栈
                /** @see HttpResponseStatus **/
                case HttpStatus.SC_OK:
                case HttpStatus.SC_UNAUTHORIZED:
                case HttpStatus.SC_NOT_FOUND:
                    log.error(logMessage);
                    break;
                default:
                    log.error(logMessage, e);
                    break;
            }
            if (response != null && ((SpringGearInterruptException) e).getResponse() == null) { // 如果是 中断异常，且 response 没有内容，尝试将 response 放入。
                ((SpringGearInterruptException) e).setResponse(response);
            }
        } else {
            log.error(logMessage, e);
        }

        throw (SpringGearException) e;
    }


}
