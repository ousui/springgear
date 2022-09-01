package com.jd.springgear.eventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.Aware;

/**
 * @author SHUAI.W
 * @since 2020/12/28
 **/
public interface EventBusAware extends Aware {

    void setEventBus(EventBus eventBus);
}
