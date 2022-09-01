package org.springgear.engine.context;

import lombok.extern.slf4j.Slf4j;

/**
 * 原样返回
 *
 * @author SHUAI.W
 * @since 2021/03/25
 **/
@Slf4j
public class SpringGearResultOriginalWrapper<R> implements SpringGearResultWrapper<Object> {
    @Override
    public Object process(Object req, Object resp, long timestamp, int code, String msg, Object... others) {
        return resp;
    }
}
