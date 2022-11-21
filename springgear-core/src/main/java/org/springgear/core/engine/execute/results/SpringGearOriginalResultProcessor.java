package org.springgear.core.engine.execute.results;

import lombok.extern.slf4j.Slf4j;
import org.springgear.core.engine.execute.SpringGearExecuteEntity;
import org.springgear.core.engine.execute.SpringGearResultProcessor;

/**
 * 原样返回
 *
 * @author SHUAI.W
 * @since 2021/03/25
 **/
@Slf4j
public class SpringGearOriginalResultProcessor<R> implements SpringGearResultProcessor<Object> {
    @Override
    public Object process(Object resp, SpringGearExecuteEntity entity) {
        return resp;
    }
}
