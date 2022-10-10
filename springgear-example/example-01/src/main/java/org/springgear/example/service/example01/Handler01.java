package org.springgear.example.service.example01;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.handler.AbstractSpringGearEndingHandler;
import org.springgear.core.handler.AbstractSpringGearHandler;

@Qualifier("example01")
@Component
public class Handler01 extends AbstractSpringGearEndingHandler<String, String> {
    @Override
    public String end(SpringGearContext<String, String> context, String s, Object... others) throws Exception {

        System.out.println("this is handler 01 --------");

        return String.format(
                "%s, this is output param and input param is: %s",
                System.currentTimeMillis(), context.getRequest()
        );
    }
}
