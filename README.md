### Проект
- Проект создан с использованием Java Spring Boot.
- Для хранения данных используется встроенная база данных H2.
- Сборка проекта осуществляется с помощью Maven.
### Запуск проекта
1. Клонируйте репозиторий.
2. Откройте проект в IntelliJ IDEA.
3. Запустите приложение Application.
### Демонстрация работы запросов
1. POST запрос:
    - Для добавления новой темы отправьте POST запрос на /topics с данными темы в формате JSON.
    - Например:
![POST_topic.gif](images%2FPOST_topic.gif)
2. PUT запрос:
    - Чтобы обновить существующую тему, отправьте PUT запрос на /topics с обновленными данными темы.
    - Например:
![PUT_topic.gif](images%2FPUT_topic.gif)
3. Остальные запросы работают подобным же образом. 