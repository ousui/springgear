package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.context.SpringGearContextValue;
import org.springgear.core.engine.executor.handler.AbstractSpringGearEndingHandler;

import java.util.List;

@Qualifier("single")
@Component
public class SingleHandler01 extends AbstractSpringGearEndingHandler<String, String> {
    @Override
    public String end(SpringGearContext<String, String> context, String s) throws Exception {

        System.out.println("this is handler 01 --------");

        context.<DSD>getValues();


        return String.format(
                "%s, this is output param and input param is: %s",
                System.currentTimeMillis(), context.getRequest()
        );
    }

    class DSD extends SpringGearContextValue {
        String a;
        List<String> b;
    }
}
