package org.springgear.core.handler;

import org.springgear.core.context.SpringGearContext;
import org.springframework.core.Ordered;

/**
 * -
 *
 * @author SHUAI.W 2018-01-10
 **/
public interface SpringGearHandler<REQ, RESP> {

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
