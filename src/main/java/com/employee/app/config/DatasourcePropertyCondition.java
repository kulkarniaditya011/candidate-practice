package com.employee.app.config;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DatasourcePropertyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String datasourceUrl=context.getEnvironment().getProperty("spring.datasource.url");

        return datasourceUrl != null && !datasourceUrl.isEmpty();
    }
}
