package com.jd.springgear.engine.handler;

import com.jd.springgear.exception.GearInterruptException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

/**
 * handler 的抽象实现。
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearHandler<REQ, RESP> implements SpringGearHandler<REQ, RESP> {

    /**
     * 迅速抛出一个中断异常
     *
     * @param msg
     * @param args
     * @throws GearInterruptException
     */
    protected void throwInterruptException(String msg, Object... args) throws GearInterruptException {
        throw new GearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage());
    }

    protected void throwInterruptException(int code, String msg, Object... args) throws GearInterruptException {
        throw new GearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage(), code);
    }

}
