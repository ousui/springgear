package org.springgear.core.engine.execute.results;

import org.springgear.core.engine.execute.SpringGearExecuteEntity;
import org.springgear.core.engine.execute.SpringGearResultProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用 map wrapper
 *
 * @author SHUAI.W
 * @since 2021/03/25
 **/
public class SpringGearMapResultProcessor implements SpringGearResultProcessor<Map<String, Object>> {

    @Override
    public Map<String, Object> process(Object resp, SpringGearExecuteEntity entity) {
        Map<String, Object> output = new HashMap<>();

        output.put("ts", entity.getTimestamp());
        output.put("msg", entity.getMsg());
//        output.put("code", entity.getSource());
        output.put("data", resp);
        return output;
    }

}
