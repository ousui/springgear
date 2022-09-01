package org.springgear.eventbus;

import org.springgear.engine.context.SpringGearContext;
import org.springgear.eventbus.annotation.SpringGearEvent;

/**
 * 基础 spring gear event 注解的监听实现
 *
 * @author SHUAI.W
 * @see SpringGearEvent
 * @since 2020/12/26
 **/
public interface SpringGearEventListener<REQ, RESP> {

    void process(SpringGearContext<REQ, RESP> context);

    void ex(SpringGearContext<REQ, RESP> context, Throwable e);
}
