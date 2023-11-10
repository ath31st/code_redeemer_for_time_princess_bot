package bot.farm.redeemer.config;

import java.util.Set;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("file:./white_set_id.properties")
public class ConfigFromFile {
  @Value("${idSet}")
  private Set<Long> idSet;
  @Value("${idGroup}")
  private Long idGroup;
}
