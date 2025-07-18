# Сервис конфигурации событий

Микросервис на Spring Boot для хранения и управления конфигурациями событий. Он разделён на слои по принципу Separation of Concerns. Ключевая идея — полностью отвязать бизнес‑логику и REST‑контроллеры от конкретных технологий хранения (MongoDB или Elasticsearch). Это достигается через абстракцию репозитория и реализацию необходимых репозиториев для каждого хранилища.

---

## Архитектура

* **Доменная модель**: чистый POJO `EventConfig` без аннотаций хранения.
* **Абстракция репозитория**: интерфейс `EventConfigRepository` задаёт CRUD и фильтрацию.
* **Адаптеры хранилищ**:

    * **MongoDB** через Spring Data MongoDB
    * **Elasticsearch** через `ElasticsearchOperations`
* **Сервисный слой**: `EventConfigService` реализует бизнес‑логику, проверку дубликатов и обработку ошибок.
* **Контроллер**: интерфейс `EventConfigController` с аннотациями API, реализация в `EventConfigControllerImpl`.
* **Конфигурация**: выбор хранилища (`mongo` или `elastic`) через профили Spring (`application-*.properties`).
* **Валидация и обработка ошибок**: Bean Validation (`@Valid`), глобальный `@ControllerAdvice`, возврат `ExceptionResponse`.
* **Документация API**: OpenAPI 3 с springdoc-openapi.

---

## Технологии

* Java 21
* Spring Boot 3.5.x
* Spring Data MongoDB & Elasticsearch
* Docker & Docker Compose
* springdoc-openapi (OpenAPI 3)
* Bean Validation (Jakarta Validation)
* Lombok

---

## Запуск

### Требования

* JDK 21
* Maven 3.8+ или Maven Wrapper
* Docker & Docker Compose

### Клонирование репозитория

```bash
git clone https://github.com/your-org/event-config-service.git
cd event-config-service
```

### Конфигурация профилей

Профиль задаёт хранилище:

| Профиль   | Хранилище     | Файл свойств                     |
| --------- | ------------- | -------------------------------- |
| `mongo`   | MongoDB       | `application-mongo.properties`   |
| `elastic` | Elasticsearch | `application-elastic.properties` |

В `application.properties`:

```properties
spring.profiles.active=${SPRING_PROFILES_ACTIVE:default}
storage.type=${spring.profiles.active}
```

### Запуск через Docker Compose

```bash
docker-compose up -d --build
```

* **mongo**: MongoDB 6.0
* **elasticsearch**: Elasticsearch 8.18.1
* **app**: сервис на порт 8080

Остановка и удаление:

```bash
docker-compose down
```

### Запуск локально

```bash
mvn clean package -DskipTests
java -Dspring.profiles.active=mongo -jar target/event-config-service.jar
```

или

```bash
java -Dspring.profiles.active=elastic -jar target/event-config-service.jar
```

---

## API

Базовый путь: `/api/v1/event-config`

### Создать конфигурацию

* **Метод**: `POST /api/v1/event-config`
* **Тело запроса** (JSON):

  ```json
  {
    "eventType": "USER_SIGNUP",
    "source": "web",
    "enabled": true
  }
  ```
* **Ответы**:

    * `201 Created` — возвращает созданный объект с полями `id`, `createdAt`, `updatedAt`
    * `400 Bad Request` — ошибки валидации
    * `409 Conflict` — дубликат

### Обновить конфигурацию

* **Метод**: `PUT /api/v1/event-config/{id}`
* **Параметр**: `id` — идентификатор конфигурации
* **Тело запроса**: аналогично POST
* **Ответы**:

    * `200 OK` — обновлённый объект
    * `400 Bad Request` — ошибки валидации
    * `404 Not Found` — не найден ID

### Получить список с фильтрацией

* **Метод**: `GET /api/v1/event-config`
* **Параметры запроса**:

    * `eventType` (необязательно)
    * `source` (необязательно)
    * `enabled` (необязательно)
    * `page`, `size`, `sort` (параметры пагинации)
* **Ответ**:

    * `200 OK` — список DTO (массив JSON)
    * `400 Bad Request` — ошибки валидации


---

## Формат сущности

```json
{
  "id": "string",
  "eventType": "string",
  "source": "string",
  "enabled": true,
  "createdAt": "2025-07-18T10:00:00",
  "updatedAt": "2025-07-18T10:00:00"
}
```

---

## Переключение хранилища

```bash
# Использовать MongoDB
SPRING_PROFILES_ACTIVE=mongo
# Использовать Elasticsearch
SPRING_PROFILES_ACTIVE=elastic
```

---

## Краткое обоснование архитектуры и переключения хранилищ

**Чистая доменная модель & интерфейс**

EventConfig — обычный POJO, а EventConfigRepository описывает CRUD и фильтрацию. Сервис и контроллеры работают только с этим интерфейсом, без привязки к конкретной БД.

**Реализации репозиториев под Mongo и Elasticsearch**

Каждое хранилище реализует свой репозиторий:

MongoEventConfigRepository через Spring Data MongoDB (@Profile("mongo")), работает с DAO-классом и коллекцией.

ElasticEventConfigRepository через ElasticsearchOperations (@Profile("elastic")), использует чистый POJO и имя индекса из ${elastic.index.event-config}.

**Переключение через профили**

Переключение через профили позволяет Spring Boot загружать только те бины, что помечены соответствующим профилем, — без if‑ов и фабрик.

**Плюсы подхода**

Модулярность: легко добавить новое хранилище.

Тестируемость: заглушить репозиторий в юнит‑тестах.

Поддерживаемость: конфигурация в свойствах, ясные границы ответственности.

---

**Автор**: Странникова Наталья
