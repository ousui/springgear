package org.springgear.core.engine.execute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.exception.SpringGearError;

import java.util.Objects;

/**
 * spring gear engine 执行器处理过程部件组成
 * 用于对 engine executor 进行数据传递和过程构建
 * 后续优化为可扩展的处理形式
 * timestamp 和 source 后续处理为构造模式自由传参
 */
@RequiredArgsConstructor
@ToString
public class SpringGearEngineParts {

    private final static String SOURCE = "_spring_gear_default_";
    private final static String DEFAULT_MSG = "ok";

    @Getter
    private final String source;
    @Getter
    private final Object[] args;
    @Getter
    private final long timestamp;
    @Getter
    @Setter
    private SpringGearError exception;

    public SpringGearEngineParts(Object[] args, String beanName, SpringGearEngine engineAnno) {
        this.args = args;
        this.timestamp = System.currentTimeMillis();
        this.source = Objects.toString(engineAnno.source(), SOURCE);
    }

    /**
     * @return
     */
    public String getMsg() {
        if (exception == null) {
            return DEFAULT_MSG;
        } else {
            return exception.getLocalizedMessage();
        }
    }

}
