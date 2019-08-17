# Сервер для игры Fortune
## Документация Waves
- [Waves Транзакция](https://docs.wavesplatform.com/ru/blockchain/transaction.html)
- [Написание dApps](https://docs.wavesplatform.com/ru/smart-contracts/writing-dapps.html)
- [RIDE скрипт](https://docs.wavesplatform.com/ru/ride/ride-script.html)
## Полезные ресурсы
- [Swagger документация для используемых api waves](https://testnode1.wavesnodes.com/api-docs/index.html)
- [Среда тестирования RIDE скриптов](https://ide.wavesplatform.com)
- [Онлайн-сервис, отображающий в понятном для человека виде данные блокчейна Waves](https://wavesexplorer.com)
- [Генерация и валидация RSA подписи онлайн](https://8gwifi.org/RSAFunctionality?rsasignverifyfunctions=rsasignverifyfunctions&keysize=512)
## Ссылка на Swagger документацию в проекте
- [Локальная ссылка на Swagger (http://localhost:8080)](http://localhost:8080/fortune/api/swagger-ui.html)
- [Открытая ссылка на Swagger (https://fortune-develop.herokuapp.com)](https://fortune-develop.herokuapp.com/fortune/api/swagger-ui.html)
## Для разработчиков
- [Доска задач](https://trello.com/b/FGwzuRNL/%D0%B4%D0%BE%D1%81%D0%BA%D0%B0-%D0%B4%D0%BB%D1%8F-%D0%BC%D0%B5%D0%BD%D0%B5%D0%B4%D0%B6%D0%BC%D0%B5%D0%BD%D1%82%D0%B0)
- [Дашборд для heroku](https://dashboard.heroku.com/apps/fortune-develop)
- [Wiki страничка по сервису RSA](https://gitlab.com/waves-games/the-sending-of-rsa-signature-to-waves-node/wikis/%D0%93%D0%B5%D0%BD%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%8F-%D0%B8-%D0%BE%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5-RSA-%D0%BF%D0%BE%D0%B4%D0%BF%D0%B8%D1%81%D0%B8-%D0%B2-%D1%81%D0%B5%D1%82%D1%8C-waves.)
- [Учетные записи](https://gitlab.com/waves-games/the-sending-of-rsa-signature-to-waves-node/wikis/%D0%A3%D1%87%D0%B5%D1%82%D0%BD%D1%8B%D0%B5-%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D0%B5-%D0%B4%D0%BB%D1%8F-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B.)
- В проекте имеется plugin для проверки code style. Главный файл настроек по стилю написания кода хранится в src/main/resources/config.yml. Команда для запуска плагина: mvn site, после выполнения данного плагина результат лежит в target/site/checkstyle.html