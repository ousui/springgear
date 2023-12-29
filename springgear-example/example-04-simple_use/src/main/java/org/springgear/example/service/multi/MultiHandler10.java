package org.springgear.example.service.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.engine.executor.handler.AbstractSpringGearOrderedHandler;

@Qualifier("multi")
@Component
public class MultiHandler10 extends AbstractSpringGearOrderedHandler<String, String> {

    @Override
    public void handle(SpringGearContext<String, String> context) throws Exception {
        System.out.println("this is multi handler 10 --------, 01 response is: " + context.getResponse());
        context.setResponse("multi 10 I change some response");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
