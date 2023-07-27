package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.IggAccount;
import bot.farm.redeemer.repository.IggAccountRepositoy;
import bot.farm.redeemer.util.RegExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IggAccountService {
  private final IggAccountRepositoy iggAccountRepositoy;

  public List<IggAccount> getAccounts() {
    return iggAccountRepositoy.findAll();
  }

  public String saveAccounts(String rawIds) {
    List<String> exists = new ArrayList<>();
    List<String> added = new ArrayList<>();

    for (Map.Entry<Long, String> e : processingRawIds(rawIds).entrySet()) {
      if (checkExistsId(e.getKey())) {
        exists.add(e.getKey() + ":" + e.getValue());
      } else {
        IggAccount ia = new IggAccount();
        ia.setIggId(e.getKey());
        ia.setLang(e.getValue());
        iggAccountRepositoy.save(ia);
        added.add(e.getKey() + ":" + e.getValue());
      }
    }

    return prepareResponseAfterSaving(added, exists);
  }

  private String prepareResponseAfterSaving(List<String> added, List<String> exists) {
    String response = null;
    if (!added.isEmpty()) {
      response = "Были добавлены следующие IGG ID: " + String.join(", ", added) + ".";
    }

    if (!exists.isEmpty() && response != null) {
      response += " В базе данных уже находятся: " + String.join(", ", exists) + ".";
    } else if (!exists.isEmpty()) {
      response = "В базе данных уже находятся: " + String.join(", ", exists) + ".";
    }

    if (added.isEmpty() && exists.isEmpty()) {
      response = "Введенные IGG ID не были добавлены в базу данных. "
          + "Проверьте формат ввода. Числа не должны быть длиннее 18 цифр, язык только eng или rus."
          + " Пример формата: 12345:rus,67890:eng";
    }
    return response;
  }

  public String deleteAccounts(String rawIds) {
    List<String> deleted = new ArrayList<>();
    List<String> absent = new ArrayList<>();

    for (Map.Entry<Long, String> e : processingRawIds(rawIds).entrySet()) {
      if (checkExistsId(e.getKey())) {
        iggAccountRepositoy.deleteById(e.getKey());
        deleted.add(e.getKey() + ":" + e.getValue());
      } else {
        absent.add(e.getKey() + ":" + e.getValue());
      }
    }
    return prepareResponseAfterDeleting(deleted, absent);
  }

  private String prepareResponseAfterDeleting(List<String> deleted, List<String> absent) {
    String response = null;
    if (!deleted.isEmpty()) {
      response = "Были удалены следующие IGG ID: " + String.join(", ", deleted) + ".";
    }

    if (!absent.isEmpty() && response != null) {
      response += " В базе данных отсутствуют: " + String.join(", ", absent) + ".";
    } else if (!absent.isEmpty()) {
      response = "В базе данных отсутствуют: " + String.join(", ", absent) + ".";
    }

    if (deleted.isEmpty() && absent.isEmpty()) {
      response = "Введенные IGG ID не были удалены из базы данных. "
          + "Проверьте формат ввода. Числа не должны быть длиннее 18 цифр, язык только eng или rus."
          + " Пример формата: 12345:rus,67890:eng";
    }
    return response;
  }

  private boolean checkExistsId(Long id) {
    return iggAccountRepositoy.existsById(id);
  }

  private Map<Long, String> processingRawIds(String rawIds) {
    return Arrays.stream(rawIds.toLowerCase().replaceAll(" ", "")
            .split(","))
        .filter(s -> s.matches(RegExpression.IGG_ID))
        .map(s -> s.split(":"))
        .collect(Collectors.toMap(s -> Long.valueOf(s[0]), s -> s[1]));
  }
}
