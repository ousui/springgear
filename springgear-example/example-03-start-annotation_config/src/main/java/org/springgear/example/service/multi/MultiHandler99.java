package org.springgear.example.service.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.handler.AbstractSpringGearEndingHandler;

@Qualifier("multi")
@Component
public class MultiHandler99 extends AbstractSpringGearEndingHandler<String, String> {
    @Override
    public String end(SpringGearContext<String, String> context, String s) throws Exception {

        System.out.println("this is multi handler end -------- 02 response is: " + context.getResponse());

        return String.format(
                "%s, this is output param and input param is: %s",
                System.currentTimeMillis(), context.getRequest()
        );
    }
}
