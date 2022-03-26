# Дипломная работа “Облачное хранилище”

## Описание проекта

Разработано приложение - REST-сервис. Сервис предоставляет REST интерфейс для возможности загрузки файлов и вывода списка уже загруженных файлов пользователя.
Все запросы к сервису авторизованы. Заранее подготовленное веб-приложение (FRONT) подключается к разработанному сервису без доработок, а также использует функционал FRONT для авторизации,
загрузки и вывода списка файлов пользователя.

## Основные свойства:
- Сервис предоставляет REST интерфейс для интеграции с FRONT
- Сервис реализовывает все методы описанные [yaml файле](./CloudServiceSpecification.yaml):
    1. Вывод списка файлов
    2. Добавление файла
    3. Удаление файла
    4. Авторизация
- Все настройки вычитываются из файла настроек (yml)
- Информация о пользователях сервиса (логины для авторизации) и данных хранятся в базе данных (PostgreSQL)

## Выполнены следующие требования к проекту:
- Приложение разработано с использованием Spring Boot
- Использован сборщик пакетов gradle/maven
- Для запуска используется docker, docker-compose
- Код размещен на github
- Код покрыт unit тестами с использованием mockito
- Добавлены интеграционные тесты с использованием testcontainers

## Запуск приложения:
Чтобы запустить приложение, перейдите в корневой каталог приложения и выполните в терминале команду:
```
docker-compose up
```
FRONT приложения будет доступен из браузера по адресу:
```
localhost:8080
