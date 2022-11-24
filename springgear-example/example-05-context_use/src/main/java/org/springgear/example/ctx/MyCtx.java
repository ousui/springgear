package org.springgear.example.ctx;

import org.springgear.core.engine.context.SpringGearContext;
import org.springgear.core.engine.execute.SpringGearEngineParts;

import java.util.Map;

public class MyCtx extends SpringGearContext<String, Map<String, String>> {

    private String vo;

    public MyCtx(SpringGearEngineParts parts) {
        super(parts);
    }

    public String getVo() {
        return vo;
    }

    public void setVo(String vo) {
        this.vo = vo;
    }
}
