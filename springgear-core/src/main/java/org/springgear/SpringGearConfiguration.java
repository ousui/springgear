package org.springgear;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springgear.core.engine.SpringGearEngineProcessor;
import org.springgear.core.engine.execute.AbstractSpringGearEngineExecutor;
import org.springgear.core.engine.execute.DefaultSpringGearEngineExecutor;

@Configuration
class SpringGearConfiguration implements ImportAware {

    private EnableSpringGear enableSpringGearAnno;

    @Bean
    public SpringGearEngineProcessor springGearEngineProcessor() {

        Class<? extends AbstractSpringGearEngineExecutor> executorClass;
        if (enableSpringGearAnno != null) {
            executorClass = enableSpringGearAnno.executor();
        } else {
            executorClass = DefaultSpringGearEngineExecutor.class;
        }

        return new SpringGearEngineProcessor(executorClass);
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        if (!importMetadata.getAnnotations().isPresent(EnableSpringGear.class)) {
            return;
        }
        this.enableSpringGearAnno = importMetadata.getAnnotations().get(EnableSpringGear.class).synthesize();
    }
}
