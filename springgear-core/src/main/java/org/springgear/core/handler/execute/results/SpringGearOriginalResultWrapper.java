package org.springgear.core.handler.execute.results;

import lombok.extern.slf4j.Slf4j;
import org.springgear.core.handler.execute.SpringGearEngineParts;
import org.springgear.core.handler.execute.SpringGearResultWrapper;
import org.springgear.exception.SpringGearError;

/**
 * 原样返回
 *
 * @author SHUAI.W
 * @since 2021/03/25
 **/
@Slf4j
public class SpringGearOriginalResultWrapper<R> implements SpringGearResultWrapper<Object> {
    @Override
    public Object process(Object resp, SpringGearError e, SpringGearEngineParts entity) {
        return resp;
    }
}
