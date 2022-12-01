package org.springgear.core.engine.execute.executors;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.context.SpringGearContextValue;
import org.springgear.core.engine.execute.SpringGearEngineExecutor;
import org.springgear.core.engine.execute.SpringGearEngineParts;
import org.springgear.core.engine.handler.SpringGearEngineInterface;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearError;
import org.springgear.exception.SpringGearException;
import org.springgear.exception.SpringGearInterruptException;

import java.util.List;

/**
 * spring gear 的应用上下处理执行器。
 *
 * @author SHUAI.W
 * @date 2017-12-13
 * @see org.springgear.core.annotation.SpringGearEngine
 **/
@Slf4j
public abstract class AbstractSpringGearEngineExecutor<RESP> implements SpringGearEngineExecutor<RESP>, BeanNameAware {

    @Setter
    private String beanName;

    /**
     * 使用的 handlers
     */
    @Setter
    private List<SpringGearEngineInterface<?, RESP>> handlers;

    @Override
    public RESP execute(SpringGearEngineParts parts) throws SpringGearError {
        if (log.isDebugEnabled()) {
            log.debug("Springgear core for bean `{}` start execute main process with handlers: {}", this.beanName, this.handlers);
        }
        if (CollectionUtils.isEmpty(handlers)) {
            log.warn("There is no handlers ware injected to bean `{}`, please check.", this.beanName);
            return null;
        }

        Object request = null;
        Object[] args = parts.getArgs();
        long timestamp = parts.getTimestamp();
        String source = parts.getSource();

        if (false == ObjectUtils.isEmpty(args)) {
            request = args[0];
        }

        SpringGearContextValue cv = parts.getContextValue();

        SpringGearContext<?, RESP> context = new SpringGearContext<>(request, args, source, timestamp, cv);


        if (log.isDebugEnabled()) { // 入参记录，方便问题跟踪，只记录一次就好。
            log.debug("TS[{}-{}] Handler '{}' start work. request is: {}", source, timestamp, beanName, request);
        }

        // 核心 handlers 循环处理
        for (int i = 0; i < handlers.size(); i++) {
            SpringGearEngineInterface handler = handlers.get(i);
            String classSimpleName = handler.getClass().getSimpleName();
            // 如果不支持，则 continue。
            if (!handler.supports(context)) {
                if (log.isDebugEnabled()) {
                    log.debug("TS[{}-{}] Handler '{}#{}' don't support.", source, timestamp, classSimpleName, i);
                }
                continue;
            }

            try {
                handler.handle(context);
            } catch (Throwable e) { // 捕获并处理异常
                this.onThrowable(context, e);
            } finally {
                if (log.isDebugEnabled()) {
                    log.debug("TS[{}-{}] Handler '{}#{}' finished work. duration {} ms. context is: {}",
                            source, timestamp, classSimpleName, i, (System.currentTimeMillis() - timestamp), context);
                }
            }
        }

        // 记录一次 context，方便线上问题排查
        if (log.isInfoEnabled()) {
            log.info("TS[{}-{}] Handler '{}' finished work. duration {} ms. context is: {}", source, timestamp, beanName, (System.currentTimeMillis() - timestamp), context);
        }

        final RESP response = context.getResponse();
        return response;
    }


    /**
     * 异常处理
     *
     * @param context
     * @param e
     * @throws SpringGearException
     */
    protected void onThrowable(SpringGearContext<?, RESP> context, Throwable e) throws SpringGearException {
        // 捕获到 continue 异常，返回继续向下执行代码。
        if (e instanceof SpringGearContinueException) {
        } else if (e instanceof SpringGearInterruptException) {
            throw (SpringGearInterruptException) e;
        } else {
            throw new SpringGearException(e.getLocalizedMessage());
        }
    }


}
