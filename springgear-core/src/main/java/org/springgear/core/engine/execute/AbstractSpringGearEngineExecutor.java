package org.springgear.core.engine.execute;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springgear.core.engine.handler.SpringGearEngineHandler;
import org.springgear.core.engine.context.EmptyRequest;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.context.SpringGearResultOriginalWrapper;
import org.springgear.core.engine.context.SpringGearResultWrapper;
import org.springgear.exception.SpringGearException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    private List<SpringGearEngineHandler<?, ?>> handlers;

    @Setter
    private SpringGearResultWrapper wrapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 将处理流程进行扩展的附加输出，不再进行耦合
        this.initResultWrapper();
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
        SpringGearContext context = new SpringGearContext(request, source, timestamp, others);

        if (log.isDebugEnabled()) { // 入参记录，方便问题跟踪，只记录一次就好。
            log.debug("TS[{}-{}] Handler '{}' start work. request is: {}", source, timestamp, beanName, request);
        }

        // 核心 handlers 循环处理
        for (int i = 0; i < handlers.size(); i++) {
            this.handleForEach(timestamp, source, context, handlers.get(i), i + 1);
        }

        // 记录一次 context，方便线上问题排查
        if (log.isInfoEnabled()) {
            log.info("TS[{}-{}] Handler '{}' finished work. duration {} ms. context is: {}", source, timestamp, beanName, (System.currentTimeMillis() - timestamp), context);
        }

        final Object response = context.getResponse();
        return response;
    }

    /**
     * 循环处理 handler
     *
     * @param timestamp
     * @param source
     * @param context
     * @param handler
     * @throws SpringGearException
     */
    private void handleForEach(long timestamp, String source, SpringGearContext context, SpringGearEngineHandler<?, ?> handler, int index) throws SpringGearException {
        String classSimpleName = handler.getClass().getSimpleName();

        // 如果不支持，则 continue。
        if (!handler.supports(context)) {
            if (log.isDebugEnabled()) {
                log.debug("TS[{}-{}] Handler '{}#{}' don't support.", source, timestamp, classSimpleName, index);
            }
            return;
        }

        try {
            handler.handle(context);
        } catch (Throwable e) { // 捕获并处理异常
            this.onThrowable(context, e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("TS[{}-{}] Handler '{}#{}' finished work. duration {} ms. context is: {}",
                        source, timestamp, classSimpleName, index, (System.currentTimeMillis() - timestamp), context);
            }
        }
    }

    @Override
    public Object wrap(Object req, Object resp, long timestamp, int code, String msg, Object... others) {

        if (log.isDebugEnabled()) {
            log.debug("start wrap parameters, req: `{}`, resp: `{}`, timestamp: `{}`, code: `{}`, msg: `{}`", req, resp, timestamp, code, msg);
        }
        return wrapper.process(req, resp, timestamp, code, msg, others);
    }

}
