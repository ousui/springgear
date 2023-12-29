package org.springgear.example.service.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.engine.executor.handler.AbstractSpringGearRequestValidHandler;

@Qualifier("multi")
@Component
public class MultiHandler00Verify extends AbstractSpringGearRequestValidHandler<String, String> {

    @Override
    public void verify(String request, SpringGearContext<String, String> context) throws IllegalArgumentException {
        System.out.println("this is multi handler 00 --------, do some verify for input");
        Assert.hasText(request, "input must has some text");
    }
}
