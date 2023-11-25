package bot.farm.redeemer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining beans.
 * This class contains bean definitions, such as an ObjectMapper bean, that can be
 * used throughout the Spring application for customization and configuration.
 */
@Configuration
public class BeanConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
