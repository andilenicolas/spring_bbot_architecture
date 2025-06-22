package tests.infrastructure.persistence;

import com.yourcompany.yourapp.infrastructure.persistence.GenericRepository;
import com.yourcompany.yourapp.testsupport.UseInMemoryDatabase;
import com.yourcompany.yourapp.testsupport.mock.UserEntityMock;

import infrastructure.persistence.repository.GenericRepositoryImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

@SpringBootTest
@UseInMemoryDatabase
class GenericRepositoryRegistrarTest {

    @Autowired
    private ApplicationContext context;

    @Autowired(required = false)
    private GenericRepository<UserEntityMock, Long> userRepository;

    @Autowired(required = false)
    private GenericRepository<PaymentEntityMock, Long> paymentReposioty;

    @Test
    void should_register_repository_for_entity_extending_base_entity() {
        assertThat(userRepository).isNotNull();
        assertThat(userRepository).isInstanceOf(GenericRepository.class)
                .withFailMessage("Expected UserEntityMock repository to be registered as GenericRepository");
        boolean contains = context.containsBean("UserEntityMockRepository");
        assertThat(contains)
                .withFailMessage("Expected Spring context to contain a bean named 'UserEntityMockRepository'")
                .isTrue();
    }

    @Test
    void shouldNotRegisterRepositoryForInvalidEntityType() {
        assertThat(paymentReposioty)
                .withFailMessage(
                        "Expected no repository to be registered for PaymentEntityMock because it is not a valid @Entity or does not extend BaseEntity")
                .isNull();
    }

    @Test
    void shouldRegisterOnlyOneUserRepositoryBean() {
        // Act: Retrieve all beans of type GenericRepository
        Map<String, GenericRepository> beans = context.getBeansOfType(GenericRepository.class);

        // Filter to only include UserEntityMock type (safely cast)
        long userRepoCount = beans.values().stream()
                .filter(bean -> bean instanceof GenericRepository<?, ?>)
                .filter(bean -> {
                    try {
                        Field entityTypeField = GenericRepositoryImpl.class.getDeclaredField("entityType");
                        entityTypeField.setAccessible(true);
                        Class<?> entityType = (Class<?>) entityTypeField.get(bean);
                        return UserEntityMock.class.equals(entityType);
                    } catch (Exception e) {
                        return false;
                    }
                }).count();

        // Assert: Only one such bean should exist
        assertThat(userRepoCount).isEqualTo(1);
    }

}
