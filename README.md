# Code redeemer for mobile game "Time Princess"

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![SonarLint](https://img.shields.io/badge/SonarLint-CB2029?style=for-the-badge&logo=sonarlint&logoColor=white)

## Что умеет

.....

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

На windows:
```shell
git clone https://github.com/ath31st/code_redeemer_for_time_princess_bot
cd code_redeemer_for_time_princess_bot
mvn package
cd target
New-Item white_set_id.properties
```
На linux:
```bash
$ git clone https://github.com/ath31st/code_redeemer_for_time_princess_bot
$ cd code_redeemer_for_time_princess_bot
$ mvn package
$ cd target
$ touch white_set_id.properties
```

## Technologies used

Versions:
- Java: 17</br>
- Spring Boot: 2.7.12</br>
- Telegrambots: 6.7.0</br>
- SQLite version: 3.42.0.0</br>
- Maven: 3.8.6</br>