# test-creator
Программа для прохождения и создания тестов. Данная программа написана на платформе JavaFX.
### Как запустить
Для работы и запуска программы должны быть установлены Maven и Java 21

1 Склонируйте либо скачайте проект.

2 Откройте терминал в папке с файлом `pom.xml`.

3 Введите команду `mvn clean javafx:run`

### Подробнее о программе
В папке `resourses/tests` вы можете найти примеры тестов в формате json. 
При запуске программы данные тесты будут загружены в программу. Если вы хотите открыть другие тесты,
прогрмма поддерживает загрузку тестов из указанной пользователем директории.


Ниже приведён пример теста в формате json
```js
{
  "name" : "Название теста",
  "questions" : [
    {
      "question" : "Текст вопроса 1",
      "options" : [
        "ответ 1", "ответ 2", "ответ 3"
      ],
      "answer" : [1], //номер ответа/ответов
      "points" : 10,  //количество баллов за вопрос
      "multipleAnswer": false //один варинат ответа - false, несколько вариантов - true
    },
    {
      "question" : "Текст вопроса 2",

      "options" : [
        "ответ 1", "ответ 2", "ответ 3"
      ],
      "answer" : [2, 3],
      "points" : 10,
      "multipleAnswer": true
    },
    ... 
```
