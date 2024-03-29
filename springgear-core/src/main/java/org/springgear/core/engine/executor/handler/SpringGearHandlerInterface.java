package org.springgear.core.engine.executor.handler;

import org.springgear.core.context.SpringGearContext;

/**
 * -
 *
 * @author SHUAI.W 2018-01-10
 **/
public interface SpringGearHandlerInterface<REQ, RESP> {

    /**
     * 是否支持本 handler 处理。
     *
     * @return t/f
     */
    default boolean supports(SpringGearContext<REQ, RESP> context) {
        return true;
    }


    /**
     * 处理程序
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    void handle(SpringGearContext<REQ, RESP> context) throws Exception;


    /**
     * TODO 定义一个异常处理器
     * @param ex
     */
    default void onException(Exception ex){
    };
}
