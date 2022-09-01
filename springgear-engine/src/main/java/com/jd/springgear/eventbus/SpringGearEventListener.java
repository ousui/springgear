package com.jd.springgear.eventbus;

import com.jd.springgear.engine.context.SpringGearContext;

/**
 * 基础 spring gear event 注解的监听实现
 *
 * @author SHUAI.W
 * @see com.jd.springgear.eventbus.annotation.SpringGearEvent
 * @since 2020/12/26
 **/
public interface SpringGearEventListener<REQ, RESP> {

    void process(SpringGearContext<REQ, RESP> context);

    void ex(SpringGearContext<REQ, RESP> context, Throwable e);
}
