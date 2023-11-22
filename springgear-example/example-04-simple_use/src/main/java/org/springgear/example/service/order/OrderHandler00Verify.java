package org.springgear.example.service.order;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springgear.core.context.SpringGearContext;
import org.springgear.impl.engine.handler.AbstractSpringGearRequestValidHandler;

@Qualifier("order")
@Component
public class OrderHandler00Verify extends AbstractSpringGearRequestValidHandler<String, String> {

    @Override
    public void verify(String request, SpringGearContext<String, String> context) throws IllegalArgumentException {
        System.out.println("this is ORDER handler 00 --------, do some verify for input");
        Assert.hasText(request, "input must has some text");
    }
}
