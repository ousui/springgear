package org.springgear.core.engine.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Optional;

/**
 * 上下文用于传递数据。如果有复杂业务可以继承 Context 添加获取 parameter 逻辑
 *
 * @author SHUAI.W 2018-01-10
 **/
@ToString
@RequiredArgsConstructor
public final class SpringGearContext<REQ, RESP> implements Serializable {

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

    /**
     * 上下文传递数据用的 value
     */
    private final SpringGearContextValue values;

    public <V extends SpringGearContextValue> V getValues() {
        return (V) values;
    }

    /**
     * 出参，读写
     */
    @Getter
    @Setter
    private RESP response;


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
    public SpringGearContext<REQ, RESP> setValue(String key, Object value) {
        this.values.put(key, value);
        return this;
    }

    public <T> T getValue(String key, T defaultValue) {
        Optional<T> value = (Optional<T>) Optional.ofNullable(this.getValues().get(key));
        return value.orElse(defaultValue);
    }

    public <T> T getValue(String key) {
        return this.getValue(key, null);
    }

}
