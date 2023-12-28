package org.springgear.core.engine.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springgear.core.annotation.SpringGearEngine;
import org.springgear.core.context.SpringGearContextValue;

import java.lang.reflect.InvocationTargetException;
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

    private final Object request;
    @Getter
    private final Object[] args;
    @Getter
    private final String source;
    @Getter
    private final long timestamp;
    @Getter
    private final SpringGearContextValue contextValue;

    @Getter
    private final String beanName;


    public SpringGearEngineParts(Object[] args, String beanName, SpringGearEngine engineAnno) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.args = args;

        if (this.args == null || this.args.length == 0) {
            this.request = null;
        } else {
            this.request = this.args[0];
        }

        this.beanName = beanName;
        this.timestamp = System.currentTimeMillis();
        this.source = Objects.toString(engineAnno.source(), SOURCE);
        Class<? extends SpringGearContextValue> contextValueClass = engineAnno.contextValueClass();
        this.contextValue = contextValueClass.getDeclaredConstructor().newInstance();
    }


    public <R> R getRequest() {
        return (R) request;
    }
}
