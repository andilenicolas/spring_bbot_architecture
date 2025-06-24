package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.core.ResolvableType;

public class typeSafeBeanRetrievalViaProvider {

    @Test
    void typeSafeBeanRetrievalViaProvider() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(DynamicServiceRegistrar.class);

        contextRunner.run(context -> {
            ResolvableType fooType = ResolvableType.forClassWithGenerics(
                    Service.class, FooService.class, Integer.class);

            // getBeanProvider lets you fetch by ResolvableType
            Service<FooService, Integer> fooSvc = context.getBeanProvider(fooType)
                    .getIfAvailable();

            assertNotNull(fooSvc, "Expected a Service<FooService,Integer> bean");
            fooSvc.methodA();
        });
    }

}
