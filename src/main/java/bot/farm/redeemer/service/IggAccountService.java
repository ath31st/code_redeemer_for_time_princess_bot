package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.IggAccount;
import bot.farm.redeemer.repository.IggAccountRepositoy;
import bot.farm.redeemer.util.RegExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IggAccountService {
  private final IggAccountRepositoy iggAccountRepositoy;

  public String saveAccounts(String rawIds) {
    List<String> exists = new ArrayList<>();
    List<String> added = new ArrayList<>();

    for (Long id : processingRawIds(rawIds)) {
      if (checkExistsId(id)) {
        exists.add(String.valueOf(id));
      } else {
        IggAccount ia = new IggAccount();
        ia.setIggId(id);
        iggAccountRepositoy.save(ia);
        added.add(String.valueOf(id));
      }
    }

    String response = "Были добавленые следующие IGG ID: " + String.join(", ", added) + ".";
    if (!exists.isEmpty()) {
      response += " В базе данных уже находятся: " + String.join(", ", exists) + ".";
    }
    return response;
  }

  public List<IggAccount> getAccounts() {
    return iggAccountRepositoy.findAll();
  }

  private boolean checkExistsId(Long id) {
    return iggAccountRepositoy.existsById(id);
  }

  private Set<Long> processingRawIds(String rawIds) {
    return Arrays.stream(rawIds.replaceAll(" ", "")
            .split(","))
        .filter(s -> s.matches(RegExpression.IGG_ID))
        .map(Long::parseLong)
        .collect(Collectors.toSet());
  }
}
