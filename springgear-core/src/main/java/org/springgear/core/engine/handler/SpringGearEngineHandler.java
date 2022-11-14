package org.springgear.core.engine.handler;

import org.springgear.core.engine.context.SpringGearContext;

/**
 * -
 *
 * @author SHUAI.W 2018-01-10
 **/
public interface SpringGearEngineHandler<REQ, RESP> {

    /**
     * 是否支持本 handler 处理。
     *
     * @return t/f
     */
    default boolean supports(SpringGearContext<REQ, RESP> context, Object... others) {
        return true;
    };

    /**
     * 处理程序
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    void handle(SpringGearContext<REQ, RESP> context, Object... others) throws Exception;


}
