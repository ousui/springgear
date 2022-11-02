package org.springgear.eventbus;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.ThreadContext;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.eventbus.annotation.SpringGearEvent;

/**
 * @author SHUAI.W
 * @since 2020/12/26
 **/
public abstract class AbstractSpringGearEventListener<REQ, RESP> implements SpringGearEventListener<REQ, RESP> {

    @Override
    public void ex(SpringGearContext<REQ, RESP> context, Throwable e) {
    }

    @Subscribe
    public final void process(Object[] data) {
        SpringGearContext<REQ, RESP> context = (SpringGearContext<REQ, RESP>) data[0];
        Throwable e = (Throwable) data[1];
        Class clazz = (Class) data[2];
        SpringGearEvent sge = (SpringGearEvent) clazz.getAnnotation(SpringGearEvent.class);

        if (sge == null) {
            return;
        }

        if (this.getClass() != sge.bean()) {
            return;
        }

        ThreadContext.put("ts", String.valueOf(context.getTimestamp()));
        if (e == null) {
            this.process(context);
        } else {
            this.ex(context, e);
        }
    }
}
