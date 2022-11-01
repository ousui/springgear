package org.springgear.core.execute;

import lombok.extern.slf4j.Slf4j;

/**
 * AbstractSpringGearEngineExecutor 的默认实现
 *
 * @author SHUAI.W
 * @since 2020/12/13
 **/
@Slf4j
public class DefaultSpringGearEngineExecutor extends AbstractSpringGearEngineExecutor {

    private final static String SOURCE = "_spring_gear_default_";

    @Override
    public String getSource() {

        return SOURCE;
    }
}
