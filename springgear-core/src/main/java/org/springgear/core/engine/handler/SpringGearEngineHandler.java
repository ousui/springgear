package org.springgear.core.engine.handler;

import org.springgear.core.engine.context.SpringGearContext;

/**
 * -
 *
 * @author SHUAI.W 2018-01-10
 **/
public interface SpringGearEngineHandler<T extends SpringGearContext> {

    /**
     * 是否支持本 handler 处理。
     *
     * @return t/f
     */
    default boolean supports(T context) {
        return true;
    }

    ;

    /**
     * 处理程序
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    void handle(T context) throws Exception;


}
