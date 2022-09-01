package com.jd.springgear.eventbus;

import org.springframework.beans.factory.Aware;

/**
 * @author SHUAI.W
 * @since 2020/12/28
 **/
public interface SpringGearEventAware extends Aware {

    void set(Class<SpringGearEventListener> listeners);
}
