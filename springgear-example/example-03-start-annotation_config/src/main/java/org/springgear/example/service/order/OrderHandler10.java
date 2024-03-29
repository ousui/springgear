package org.springgear.example.service.order;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.engine.executor.handler.AbstractSpringGearHandler;

@Qualifier("order")
@Component
@Order(01)
public class OrderHandler10 extends AbstractSpringGearHandler<String, String> {

    @Override
    public void handle(SpringGearContext<String, String> context) throws Exception {
        System.out.println("this is ORDER handler 10 --------, pre response is: " + context.getResponse());
        context.setResponse("ORDER 10 I change some response");
    }
}
