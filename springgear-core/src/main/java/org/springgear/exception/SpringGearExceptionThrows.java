package org.springgear.exception;

import org.slf4j.helpers.MessageFormatter;

public interface SpringGearExceptionThrows<T> {

    /**
     * 迅速抛出一个继续操作异常
     *
     * @param msg
     * @param args
     * @throws SpringGearInterruptException
     */
    default void continueException(String msg, Object... args) throws SpringGearContinueException {
        SpringGearExceptions.throwContinueException(msg, args);
    }

    /**
     * 迅速抛出一个中断异常
     *
     * @param msg
     * @param args
     * @throws SpringGearInterruptException
     */
    default void interruptException(String msg, Object... args) throws SpringGearInterruptException {
        SpringGearExceptions.throwInterruptException(msg, args);
    }

    /**
     * 迅速抛出一个带 code 的异常
     *
     * @param code
     * @param msg
     * @param args
     * @throws SpringGearInterruptException
     */
    default void interruptException(T code, String msg, Object... args) throws SpringGearInterruptException {
        SpringGearExceptions.throwInterruptException(code, msg, args);
    }

}
