package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springgear.core.context.SpringGearContext;
import org.springgear.core.engine.executor.handler.AbstractSpringGearHandler;
import org.springgear.example.ctx.MyCtxVal;

@Qualifier("single")
@Component
public class SingleHandler01 extends AbstractSpringGearHandler<String, String> implements Ordered {


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
