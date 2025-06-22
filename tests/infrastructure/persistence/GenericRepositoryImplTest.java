package tests.infrastructure.persistence;

import com.yourcompany.yourapp.core.config.annotations.UseInMemoryDatabase;
import com.yourcompany.yourapp.infrastructure.persistence.base.BaseEntity;
import com.yourcompany.yourapp.infrastructure.persistence.base.GenericRepositoryImpl;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@UseInMemoryDatabase
class GenericRepositoryImplTest {

    @Autowired
    private GenericRepositoryImpl<ExampleEntity, Long> repository;

    @BeforeEach
    void setUp() {
        repository.setEntityType(ExampleEntity.class);
    }

    @Test
    void shouldSaveAndFindByIdSuccessfully() {
        ExampleEntity saved = repository.save(new ExampleEntity("Alice"));
        Optional<ExampleEntity> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
    }

    @Test
    void shouldFindAllEntities() {
        repository.save(new ExampleEntity("A"));
        repository.save(new ExampleEntity("B"));

        List<ExampleEntity> all = repository.findAll();

        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldUpdateEntity() {
        ExampleEntity entity = repository.save(new ExampleEntity("Before"));
        entity.setName("After");

        ExampleEntity updated = repository.update(entity);

        assertThat(updated.getName()).isEqualTo("After");
    }

    @Test
    void shouldSoftDeleteEntity() {
        ExampleEntity entity = repository.save(new ExampleEntity("SoftDelete"));

        repository.delete(entity);

        Optional<ExampleEntity> found = repository.findById(entity.getId());
        assertThat(found).isPresent();
        assertThat(found.get().isDeleted()).isTrue();
    }

    @Test
    void shouldFindByField() {
        repository.save(new ExampleEntity("Bob"));

        List<ExampleEntity> found = repository.findByField("name", "Bob");

        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getName()).isEqualTo("Bob");
    }

    // === Test Entity for Isolation ===

    @Entity(name = "ExampleEntity")
    public static class ExampleEntity extends BaseEntity {
        private String name;

        public ExampleEntity() {
        }

        public ExampleEntity(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
