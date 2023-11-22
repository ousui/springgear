package org.springgear.core.engine.wrapper;

import org.springgear.core.engine.request.SpringGearEngineParts;
import org.springgear.exception.SpringGearError;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用 map wrapper
 *
 * @author SHUAI.W
 * @since 2021/03/25
 **/
public class SpringGearMapResultWrapper implements SpringGearResultWrapper<Map<String, Object>> {

    @Override
    public Map<String, Object> process(Object resp, SpringGearError e, SpringGearEngineParts entity) {
        Map<String, Object> output = new HashMap<>();

        output.put("success", e == null);
        output.put("ts", entity.getTimestamp());
        output.put("msg", e == null ? "ok" : e.getLocalizedMessage());
//        output.put("code");
        output.put("data", e == null ? resp : e);
        return output;
    }

}
