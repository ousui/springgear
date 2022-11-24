package org.springgear.core.engine.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearInterruptException;

/**
 * handler 的抽象实现。
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearHandler<REQ, RESP> implements SpringGearEngineHandler<SpringGearContext<REQ, RESP>> {


    /**
     * 迅速抛出一个继续操作异常
     *
     * @param msg
     * @param args
     * @throws SpringGearInterruptException
     */
    protected void throwContinueException(String msg, Object... args) throws SpringGearContinueException {
        throw new SpringGearContinueException(MessageFormatter.arrayFormat(msg, args).getMessage());
    }

    /**
     * 迅速抛出一个中断异常
     *
     * @param msg
     * @param args
     * @throws SpringGearInterruptException
     */
    protected void throwInterruptException(String msg, Object... args) throws SpringGearInterruptException {
        throw new SpringGearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage());
    }

    protected void throwInterruptException(int code, String msg, Object... args) throws SpringGearInterruptException {
        throw new SpringGearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage(), code);
    }

}
