package bot.farm.redeemer.service;

import bot.farm.redeemer.entity.IggAccount;
import bot.farm.redeemer.repository.IggAccountRepository;
import bot.farm.redeemer.util.RegExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing IGG (Indie Gala Gift) accounts.
 * This service provides methods for retrieving, saving, and deleting IggAccount entities.
 * It includes functionality to process raw input, validate IGG IDs, and generate response
 * messages based on the outcome of operations.
 */
@Service
@RequiredArgsConstructor
public class IggAccountService {
  private final IggAccountRepository iggAccountRepository;

  /**
   * Retrieves a list of all IGG accounts from the database.
   *
   * @return List of IggAccount entities.
   */
  public List<IggAccount> getAccounts() {
    return iggAccountRepository.findAll();
  }

  /**
   * Saves IGG accounts based on the provided raw input.
   *
   * @param rawIds Raw input containing IGG IDs and languages.
   * @return A response message indicating which accounts were added and which already existed.
   */
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
        iggAccountRepository.save(ia);
        added.add(e.getKey() + ":" + e.getValue());
      }
    }

    return prepareResponseAfterSaving(added, exists);
  }

  /**
   * Prepares a response message after saving IGG IDs.
   *
   * @param added  List of IGG IDs that were successfully added.
   * @param exists List of IGG IDs that already exist in the database.
   * @return The response message.
   */
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

  /**
   * Deletes IGG accounts based on the provided raw input.
   *
   * @param rawIds Raw input containing IGG IDs and languages.
   * @return A response message indicating which accounts were deleted and which were absent.
   */
  public String deleteAccounts(String rawIds) {
    List<String> deleted = new ArrayList<>();
    List<String> absent = new ArrayList<>();

    for (Map.Entry<Long, String> e : processingRawIds(rawIds).entrySet()) {
      if (checkExistsId(e.getKey())) {
        iggAccountRepository.deleteById(e.getKey());
        deleted.add(e.getKey() + ":" + e.getValue());
      } else {
        absent.add(e.getKey() + ":" + e.getValue());
      }
    }
    return prepareResponseAfterDeleting(deleted, absent);
  }

  /**
   * Prepares a response message after deleting IGG IDs.
   *
   * @param deleted List of IGG IDs that were successfully deleted.
   * @param absent  List of IGG IDs that were not found in the database.
   * @return The response message.
   */
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

  /**
   * Checks if an IGG ID exists in the database.
   *
   * @param id The IGG ID to check.
   * @return {@code true} if the IGG ID exists, {@code false} otherwise.
   */
  private boolean checkExistsId(Long id) {
    return iggAccountRepository.existsById(id);
  }

  /**
   * Process a raw input string containing IGG IDs and their corresponding languages.
   *
   * @param rawIds The raw input string.
   * @return A map of IGG IDs and their corresponding languages.
   */
  private Map<Long, String> processingRawIds(String rawIds) {
    return Arrays.stream(rawIds.toLowerCase().replaceAll(" ", "")
            .split(","))
        .filter(s -> s.matches(RegExpression.IGG_ID))
        .map(s -> s.split(":"))
        .collect(Collectors.toMap(s -> Long.valueOf(s[0]), s -> s[1]));
  }
}
