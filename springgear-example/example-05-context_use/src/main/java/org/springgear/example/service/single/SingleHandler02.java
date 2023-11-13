package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.impl.engine.handler.AbstractSpringGearOrderedHandler;
import org.springgear.example.ctx.MyCtxVal;

@Qualifier("single")
@Component
public class SingleHandler02 extends AbstractSpringGearOrderedHandler<String, String> {


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
