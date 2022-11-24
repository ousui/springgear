package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.handler.AbstractSpringGearEndingHandler;
import org.springgear.core.engine.handler.SpringGearEngineHandler;
import org.springgear.example.ctx.MyCtx;

@Qualifier("single")
@Component
public class SingleHandler01 implements SpringGearEngineHandler<MyCtx> {

    @Override
    public void handle(MyCtx context) throws Exception {
        context.setVo("my hello world");
    }
}
