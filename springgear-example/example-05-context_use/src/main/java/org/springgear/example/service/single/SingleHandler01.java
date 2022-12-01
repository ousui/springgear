package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.handler.AbstractSpringGearHandler;
import org.springgear.core.engine.handler.SpringGearEngineInterface;
import org.springgear.example.ctx.MyCtxVal;

@Qualifier("single")
@Component
public class SingleHandler01 extends AbstractSpringGearHandler<String, String> {


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void handle(SpringGearContext<String, String> context) throws Exception {
        MyCtxVal v = context.getValues();
        v.setSomeLong(199929993999l);
        v.setSomeString("I pu some thing to MyCtxVal");
    }
}
