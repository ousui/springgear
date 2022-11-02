package org.springgear.example.service.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.handler.AbstractSpringGearHandler;

@Qualifier("multi")
@Component
public class MultiHandler01 extends AbstractSpringGearHandler<String, String> {

    @Override
    public void handle(SpringGearContext<String, String> context, Object... others) throws Exception {
        context.setParameter("p1", "some params");
        System.out.println("this is multi handler 01 --------");
        context.setResponse("multi 01 I do some good things.");
    }

}
