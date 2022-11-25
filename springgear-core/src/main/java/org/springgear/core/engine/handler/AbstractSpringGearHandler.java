package org.springgear.core.engine.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.Ordered;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.context.SpringGearContextTest;
import org.springgear.exception.SpringGearContinueException;
import org.springgear.exception.SpringGearExceptionThrows;
import org.springgear.exception.SpringGearInterruptException;

import java.io.Serializable;

/**
 * handler 的抽象实现。
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearHandler<T extends SpringGearContext<?, ?>, E>
        implements SpringGearEngineHandler<T>, SpringGearExceptionThrows<E>, Ordered {

}