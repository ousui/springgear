package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.handler.AbstractSpringGearEndingHandler;

@Qualifier("single")
@Component
public class SingleHandler01 extends AbstractSpringGearEndingHandler<String, String> {
    @Override
    public String end(SpringGearContext<String, String> context, String s, Object... others) throws Exception {

        System.out.println("this is handler 01 --------");

        return String.format(
                "%s, this is output param and input param is: %s",
                System.currentTimeMillis(), context.getRequest()
        );
    }
}
