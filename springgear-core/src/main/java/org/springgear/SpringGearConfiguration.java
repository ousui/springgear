package org.springgear;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springgear.core.engine.SpringGearEngineBeanProcessor;
import org.springgear.impl.engine.execute.DefaultSpringGearEngineExecutor;

@Configuration
class SpringGearConfiguration implements ImportAware {

    private EnableSpringGear enableSpringGearAnno;

    @Bean
    public SpringGearEngineBeanProcessor springGearEngineProcessor() {
        // auto find executor
        return new SpringGearEngineBeanProcessor(DefaultSpringGearEngineExecutor.class);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        if (!importMetadata.getAnnotations().isPresent(EnableSpringGear.class)) {
            return;
        }
        this.enableSpringGearAnno = importMetadata.getAnnotations().get(EnableSpringGear.class).synthesize();
    }
}
