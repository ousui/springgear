package org.springgear.core.handler.execute;

import org.springgear.exception.SpringGearError;

/**
 * 出参处理接口
 * <p>
 * 系统会自动寻找命名为 springgear.wrapper.default 的默认处理器作为结果返回处理器
 *
 * @author SHUAI.W
 * @since 2021/03/25
 **/
public interface SpringGearResultWrapper<R> {

    /**
     * 默认 bean name，获取不到的话，则使用 original
     */
    String DEFAULT_BEAN_NAME = "springgear.result.processor.default";

    /**
     * 结果包裹器
     *
     * @return
     */
    R process(Object resp, SpringGearError ex, SpringGearEngineParts entity);

}
