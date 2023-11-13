package org.springgear.core.handler.execute;

import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearError;
import org.springgear.exception.SpringGearException;
import org.springgear.exception.SpringGearInterruptException;

/**
 * 接口 工作流，继承于 pipeline, 有自己独特的实现。
 *
 * @author SHUAI.W 2017-12-13
 **/
public interface SpringGearEngineExecutor<RESP> {


    /**
     * 简化方式，只传递 request 即可。
     *
     * @param parts 入参
     * @return 返回出参
     */
    RESP execute(SpringGearEngineParts parts) throws SpringGearError;

    /**
     * 异常处理
     * TODO 使用 异常处理 Builder
     *
     * @param context
     * @param e
     * @throws SpringGearException
     */
    default void onThrowable(SpringGearContext<?, RESP> context, Throwable e) throws SpringGearException {
        // 捕获到 continue 异常，返回继续向下执行代码。
        if (e instanceof SpringGearContinueException) {
        } else if (e instanceof SpringGearInterruptException) {
            throw (SpringGearInterruptException) e;
        } else {
            throw new SpringGearException(e.getLocalizedMessage());
        }
    }

}
