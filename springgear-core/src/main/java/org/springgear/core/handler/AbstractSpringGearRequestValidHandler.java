package org.springgear.core.handler;

import org.springgear.core.context.SpringGearContext;
import org.springgear.exception.SpringGearInterruptException;
import org.springgear.support.constants.HttpStatus;

/**
 * AbstractSpringGearHandler 的 有一层抽象，主要是实现了验证方法。
 *
 * @author SHUAI.W 2017-12-13
 **/
public abstract class AbstractSpringGearRequestValidHandler<REQ, RESP> extends AbstractSpringGearHandler<REQ, RESP> {

    public abstract void verify(REQ request, Object... others) throws IllegalArgumentException;

    @Override
    public void handle(SpringGearContext<REQ, RESP> context, Object... others) throws SpringGearInterruptException {
        try {
            this.verify(context.getRequest(), others);
        } catch (IllegalArgumentException e) {
            this.throwInterruptException(HttpStatus.SC_BAD_REQUEST, "参数验证错误：{}。", e.getLocalizedMessage());
        }
    }


    @Override
    public final int getOrder() {
        return HIGHEST_PRECEDENCE + 10000;
    }


}
