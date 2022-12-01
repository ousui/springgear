package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.handler.AbstractSpringGearHandler;
import org.springgear.core.engine.handler.SpringGearEngineInterface;
import org.springgear.example.ctx.MyCtxVal;

import java.util.HashMap;
import java.util.Map;

@Qualifier("single")
@Component
public class SingleHandler02 extends AbstractSpringGearHandler<String, String> {


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void handle(SpringGearContext<String, String> context) throws Exception {
        System.out.println(":1:" + context.<MyCtxVal>getValues().getSomeLong());
        System.out.println(":2:" + context.<MyCtxVal>getValues().getSomeString());
    }
}
