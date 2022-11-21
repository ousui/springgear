package org.springgear.core.engine.execute;

import lombok.*;
import org.springgear.exception.SpringGearException;

/**
 * spring gear engine 执行器处理过程部件组成
 */
@RequiredArgsConstructor
@ToString
public class SpringGearExecuteEntity {

    private final static String SOURCE = "_spring_gear_default_";

    @Getter
    private final Object[] args;

    @Getter
    private final String source;

    @Getter
    private final long timestamp;

    @Getter
    @Setter
    private SpringGearException exception;

    /**
     * @return
     */
    public String getMsg() {
        if (exception == null) {
            return "";
        } else {
            return exception.getLocalizedMessage();
        }
    }

}
