package org.springgear.core.engine.handler;

import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.exception.SpringGearExceptions;
import org.springgear.exception.SpringGearInterruptException;
import org.springgear.support.constants.HttpStatus;

/**
 * AbstractSpringGearHandler 的 有一层抽象，主要是实现了验证方法。
 *
 * @author SHUAI.W 2017-12-13
 **/
public abstract class AbstractSpringGearRequestValidHandler<REQ, RESP> extends AbstractSpringGearOrderedHandler<REQ, RESP> {

    public abstract void verify(REQ request) throws IllegalArgumentException;

    @Override
    public void handle(SpringGearContext<REQ, RESP> context) throws SpringGearInterruptException {
        try {
            this.verify(context.getRequest());
        } catch (IllegalArgumentException e) {
            SpringGearExceptions.throwInterruptException(HttpStatus.SC_BAD_REQUEST, "参数验证错误：{}。", e.getLocalizedMessage());
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
