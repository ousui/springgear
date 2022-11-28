package org.springgear.example.service.single;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springgear.core.engine.handler.SpringGearEngineInterface;
import org.springgear.example.ctx.MyCtx;

import java.util.HashMap;
import java.util.Map;

@Qualifier("single")
@Component
public class SingleHandler02 implements SpringGearEngineInterface<MyCtx> {

    @Override
    public void handle(MyCtx context) throws Exception {
        System.out.println(context.getVo());
        Map<String, String> map = new HashMap<>();
        map.put("input", context.getRequest());
        map.put("process_vo", context.getVo());
        map.put("output", "this map");
        context.setResponse(map);
    }
}
