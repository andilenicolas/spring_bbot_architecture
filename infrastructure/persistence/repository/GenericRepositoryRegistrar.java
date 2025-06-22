package com.yourcompany.yourapp.infrastructure.persistence.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class GenericRepositoryRegistrar implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        EntityManager entityManager = applicationContext.getBean(EntityManager.class);
        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) applicationContext
                .getAutowireCapableBeanFactory();
        GenericRepositoryImpl<?> genericRepoBean = applicationContext.getBean(GenericRepositoryImpl.class);

        Metamodel metamodel = entityManager.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();

        for (EntityType<?> entity : entities) {
            if (!BaseEntity.class.isAssignableFrom(entity.getJavaType())) {
                continue;
            }

            GenericRepositoryImpl<?> repoInstance = new GenericRepositoryImpl<>();
            repoInstance.setEntityType((Class<BaseEntity>) entity.getJavaType());

            beanFactory.autowireBean(repoInstance);
            String beanName = entity.getJavaType().getSimpleName() + "Repository";

            beanFactory.registerSingleton(beanName, repoInstance);
        }
    }
}
