package com.example.demo;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * Registers one ServiceImpl<T> bean for each T in TYPES.
 */
@Configuration
public class DynamicServiceRegistrar implements BeanDefinitionRegistryPostProcessor {

    // root package to scan for your BaseService subclasses
    private static final String ROOT_PACKAGE = "com.example.demo";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
            throws BeansException {

        List<Class<? extends BaseService>> TYPES = findServiceTypes();
        for (Class<?> type : TYPES) {
            // 1) Create the bean definition
            RootBeanDefinition bd = new RootBeanDefinition(ServiceImpl.class);

            // get the ConstructorArgumentValues and add each arg in its own statement
            ConstructorArgumentValues cav = bd.getConstructorArgumentValues();
            cav.addIndexedArgumentValue(0, type);
            cav.addIndexedArgumentValue(1, Integer.class);

            // 2) Tell Spring this bean is a Service<T,ID>
            bd.setTargetType(
                    ResolvableType.forClassWithGenerics(Service.class, type, Integer.class));

            // 3) Register under e.g. "fooService", "barService"
            String beanName = type.getSimpleName().toLowerCase() + "Service";
            registry.registerBeanDefinition(beanName, bd);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // no-op
    }

    /**
     * Scan the classpath under ROOT_PACKAGE and return all non-abstract
     * subclasses of BaseService.
     */
    @SuppressWarnings("unchecked")
    private List<Class<? extends BaseService>> findServiceTypes() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(BaseService.class));

        List<Class<? extends BaseService>> types = new ArrayList<>();
        for (var bd : scanner.findCandidateComponents(ROOT_PACKAGE)) {
            try {
                Class<?> cls = Class.forName(bd.getBeanClassName());
                if (!cls.isInterface()
                        && !Modifier.isAbstract(cls.getModifiers())
                        && BaseService.class.isAssignableFrom(cls)) {

                    types.add((Class<? extends BaseService>) cls);
                }
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Failed to load class " + bd.getBeanClassName(), e);
            }
        }
        return types;
    }
}
