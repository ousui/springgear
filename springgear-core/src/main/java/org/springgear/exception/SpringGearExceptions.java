package org.springgear.exception;

import org.slf4j.helpers.MessageFormatter;

public final class SpringGearExceptions {
    public static void throwContinueException(String msg, Object... args) throws SpringGearContinueException {
        throw new SpringGearContinueException(MessageFormatter.arrayFormat(msg, args).getMessage());
    }

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

    public static void throwInterruptException(Object code, String msg, Object... args) throws SpringGearInterruptException {
        throw new SpringGearInterruptException(MessageFormatter.arrayFormat(msg, args).getMessage(), code);
    }

    public static void throwException(String msg, Object... args) throws SpringGearException {
        throw new SpringGearException(MessageFormatter.arrayFormat(msg, args).getMessage());
    }

    public static void throwError(String msg, Object... args) throws SpringGearError {
        throw new SpringGearError(MessageFormatter.arrayFormat(msg, args).getMessage());
    }
}
