package org.springgear.example.service.order;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.handler.AbstractSpringGearHandler;

@Qualifier("order")
@Component
@Order(10)
public class OrderHandler01 extends AbstractSpringGearHandler<String, String> {

    @Override
    public void handle(SpringGearContext<String, String> context, Object... others) throws Exception {
        context.setParameter("p1", "some params");
        System.out.println("this is ORDER handler 01 --------, pre response is: " + context.getResponse());
        context.setResponse("ORDER 01 I do some good things.");
    }

}
