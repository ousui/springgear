package org.springgear.example.service.order;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.engine.executor.handler.AbstractSpringGearEndingHandler;

@Qualifier("order")
@Component
public class OrderHandler99 extends AbstractSpringGearEndingHandler<String, String> {
    @Override
    public String end(SpringGearContext<String, String> context, String s) throws Exception {

        System.out.println("this is ORDER handler end -------- 02 response is: " + context.getResponse());

        return String.format(
                "%s, this is output param and input param is: %s",
                System.currentTimeMillis(), context.getRequest()
        );
    }
}
