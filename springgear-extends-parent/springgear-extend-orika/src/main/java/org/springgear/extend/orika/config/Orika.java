package org.springgear.extend.orika.config;

import org.springgear.extend.orika.OrikaBeanMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.annotation.Bean;

/**
 * @author SHUAI.W
 * @since 2021/03/30
 **/
public class Orika {

    @Bean
    public MapperFacade mapperFacade() {
        return new OrikaBeanMapper();
    }

}
