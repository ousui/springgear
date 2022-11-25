package org.springgear.core.engine.context;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;
import org.springgear.core.engine.execute.SpringGearEngineParts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 上下文用于传递数据。如果有复杂业务可以继承 Context 添加获取 parameter 逻辑
 *
 * @author SHUAI.W 2018-01-10
 **/
@ToString
public class SpringGearContext<REQ, RESP> implements Serializable {

    /**
     * 入参，只读
     */
    @Getter
    private final REQ request;
    /**
     * 所有入参
     */
    @Getter
    private final Object[] args;
    /**
     * 来源，只读
     */
    @Getter
    private final String source;
    /**
     * 时间戳，只读
     */
    @Getter
    private final long timestamp;

    @Getter
    private final Map<String, Object> values;
    /**
     * 出参，读写
     */
    @Getter
    @Setter
    private RESP response;

    public SpringGearContext(SpringGearEngineParts parts) {
        this.args = parts.getArgs();
        if (this.args == null || this.args.length == 0) {
            this.request = null;
        } else {
            this.request = (REQ) this.args[0];
        }
        this.source = parts.getSource();
        this.timestamp = parts.getTimestamp();
        this.values = new HashMap<>();
    }


    public <T> T getArgument(int position) {
        Assert.notEmpty(args, "[dev] May be your method have no any arguments?");
        Assert.isTrue(position >= 0, "[dev] the position must is not null!");
        Assert.isTrue(position < args.length,
                String.format("[dev] The arguments length is %s, but your position is %s, may be no Object?", args.length, position)
        );
        return (T) this.args[position];
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     * @return
     */
    public SpringGearContext<REQ, RESP> setParameter(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public <T> T getParameter(String key, T defaultValue) {
        Optional<T> value = (Optional<T>) Optional.ofNullable(values.get(key));
        return value.orElse(defaultValue);
    }

    public <T> T getParameter(String key) {
        return this.getParameter(key, null);
    }

}
