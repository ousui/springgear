package org.springgear.example.service.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.impl.engine.handler.AbstractSpringGearHandler;

@Qualifier("multi")
@Component
public class MultiHandler10 extends AbstractSpringGearHandler<String, String> {

    @Override
    public void handle(SpringGearContext<String, String> context) throws Exception {
        System.out.println("this is multi handler 10 --------, 01 response is: " + context.getResponse());
        context.setResponse("multi 10 I change some response");
    }
}
