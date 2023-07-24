package bot.farm.redeemer.config;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("file:./white_set_id.properties")
public class IdSetReader {
  @Value("${idSet}")
  private Set<Long> idSet;

  public Set<Long> getIdSet() {
    return idSet;
  }
}
