package bot.farm.redeemer.config;

import java.util.Set;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Configuration class for reading properties from the "white_set_id.properties" file.
 * This class is annotated with {@code @Component} to indicate that it is a Spring
 * component and can be automatically scanned and registered by the Spring container.
 * The {@code @PropertySource} annotation specifies the location of the properties file.
 * The {@code @Getter} annotation is from Lombok and automatically generates getter methods
 * for the fields.
 */
@Getter
@Component
@PropertySource("file:./white_set_id.properties")
public class ConfigFromFile {
  @Value("${idSet}")
  private Set<Long> idSet;
  @Value("${idGroup}")
  private Long idGroup = 0L;
}
