package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.handler.SpringGearEngineInterface;
import org.springgear.example.ctx.MyCtx;

@Qualifier("single")
@Component
public class SingleHandler01 implements SpringGearEngineInterface<MyCtx> {

    @Override
    public void handle(MyCtx context) throws Exception {
        context.setVo("my hello world");
    }
}
