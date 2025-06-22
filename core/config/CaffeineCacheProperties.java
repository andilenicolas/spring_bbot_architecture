import com.yourcompany.yourapp.core.config.annotations.AppConfig;
import lombok.Getter;
import lombok.Setter;

@AppConfig("cache.caffeine")
@Getter
@Setter
public class CaffeineCacheProperties {
    private int expireAfterWriteMinutes;
    private int maximumSize;
}