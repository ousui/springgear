package org.springgear.example.service.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.impl.engine.handler.AbstractSpringGearRequestValidHandler;

@Qualifier("multi")
@Component
public class MultiHandler00Verify extends AbstractSpringGearRequestValidHandler<String, String> {


    @Override
    public void verify(String request, SpringGearContext<String, String> context) throws IllegalArgumentException {

    }
}
