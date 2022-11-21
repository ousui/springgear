package org.springgear.exception;

import org.slf4j.helpers.MessageFormatter;

public final class SpringGearExceptions {

    /**
     * 迅速抛出一个中断异常
     *
     * @param msg
     * @param args
     * @throws SpringGearInterruptException
     */
    public static void throwInterruptException(String msg, Object... args) throws SpringGearInterruptException {
        throw new SpringGearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage());
    }

    public static void throwInterruptException(int code, String msg, Object... args) throws SpringGearInterruptException {
        throw new SpringGearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage(), code);
    }

}
