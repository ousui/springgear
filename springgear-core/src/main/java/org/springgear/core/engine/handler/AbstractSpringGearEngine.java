package org.springgear.core.engine.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.exception.SpringGearExceptionThrows;

/**
 * handler 的抽象实现。
 *
 * @author SHUAI.W 2018-01-10
 **/
@Slf4j
public abstract class AbstractSpringGearEngine<T extends SpringGearContext<?, ?>, E>
        implements SpringGearEngineInterface<T>, SpringGearExceptionThrows<E>, Ordered {

    @Override
    public int getOrder() {
        return 0;
    }
}