package org.springgear;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springgear.engine.SpringGearEngineProcessor;
import org.springgear.impl.engine.execute.DefaultSpringGearEngineExecutor;

@Configuration
class SpringGearConfiguration implements ImportAware {

    private EnableSpringGear enableSpringGearAnno;

    @Bean
    public SpringGearEngineProcessor springGearEngineProcessor() {
        // auto find executor
        return new SpringGearEngineProcessor(DefaultSpringGearEngineExecutor.class);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        if (!importMetadata.getAnnotations().isPresent(EnableSpringGear.class)) {
            return;
        }
        this.enableSpringGearAnno = importMetadata.getAnnotations().get(EnableSpringGear.class).synthesize();
    }
}
