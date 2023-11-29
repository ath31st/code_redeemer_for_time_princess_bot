package bot.farm.redeemer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application for code redemption in Time Princess game.
 * This class serves as the main entry point for the Spring Boot application.
 * It is
 * annotated with {@code @SpringBootApplication}, indicating that it is a Spring Boot
 * application and should be run using the {@code SpringApplication.run} method.
 */
@SpringBootApplication
public class CodeRedeemerForTimePrincess {

  public static void main(String[] args) {
    SpringApplication.run(CodeRedeemerForTimePrincess.class, args);
  }

}
