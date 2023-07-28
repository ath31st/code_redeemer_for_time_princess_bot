# Code redeemer for mobile game "Time Princess"

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![SonarLint](https://img.shields.io/badge/SonarLint-CB2029?style=for-the-badge&logo=sonarlint&logoColor=white)

## Что умеет

Данный бот предназначен для удобной активации промокодов сразу на группу игроков для игры "Time
Princess"

## Список команд

/menu - служит для вызова меню</br>

## Примеры работы

Меню:</br>
![image info](images/menu.jpg)</br>
Ввод IGG ID:</br>
![image info](images/input_id.jpg)</br>
Неправильный ввод IGG ID:</br>
![image info](images/wrong_input_id.jpg)</br>
Список IGG ID из базы данных:</br>
![image info](images/list_ids.jpg)</br>

## Как запустить

Перед тем как использовать скрипты, не забудьте заменить их параметры на свои данные (имя и токен
бота, id телеграм-чатов, которые будут иметь доступ к администраторским функциям (id перечисляются
через запятую, без пробелов))
На windows:

```shell
git clone https://github.com/ath31st/code_redeemer_for_time_princess_bot
cd code_redeemer_for_time_princess_bot
.\mvnw package
cd target
New-Item white_set_id.properties
Set-Content -Path .\white_set_id.properties -Value idSet=YOUR_TELEGRAM_CHAT_ID
java -jar .\redeemer-0.8.jar --telegram.bot.name=YOUR_BOT_NAME --telegram.bot.token=YOUR_BOT_TOKEN
```

На linux:

```bash
$ git clone https://github.com/ath31st/code_redeemer_for_time_princess_bot
$ cd code_redeemer_for_time_princess_bot
$ mvn package
$ cd target
$ touch white_set_id.properties
$ echo "idSet=YOUR_TELEGRAM_CHAT_ID" > white_set_id.properties
$ java -jar .\redeemer-0.8.jar --telegram.bot.name=YOUR_BOT_NAME --telegram.bot.token=YOUR_BOT_TOKEN
```

## Technologies used

Versions:

- Java: 17</br>
- Spring Boot: 2.7.12</br>
- Telegrambots: 6.7.0</br>
- SQLite version: 3.42.0.0</br>
- Maven: 3.8.6</br>