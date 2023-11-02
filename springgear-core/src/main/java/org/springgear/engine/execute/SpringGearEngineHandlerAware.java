package org.springgear.engine.execute;

import org.springframework.beans.factory.Aware;
import org.springgear.engine.handler.SpringGearEngineInterface;

import java.util.List;

/**
 * 负责自动注入 handlers
 */
public interface SpringGearEngineHandlerAware extends Aware {

    String PROPERTY_FIELD_NAME = "handlers";

    /**
     * @param handlers
     */
    void setHandlers(List<SpringGearEngineInterface<?, ?>> handlers);

}
