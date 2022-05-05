# MakudVkCheck
Плагин добавляет команду проверки привязки аккаунта к вк и может выдавать награду за привязку к вк.

Функционал:
- **Команда /vkcheck - проверка на привязку.**
- **Команда /vkreward - получить награду.**
- **Настройка наград в конфиге.**

Настройка:

```
mysql:
  user: root # Имя пользователя для доступа к Mysql
  port: 3306 # Порт на котором запущен MysqlServer
  password: 'qwerty123' # Пароль от базы данных
  database: testdatabase1 # База данных
  table: vk_minecraft_players # Таблица где есть ники игроков которые привязали аккаунт к вк
  players-field: 'player-list' # Название поля с никами в таблице

messages:
  check:
    vk_null: '&aПроверка данных... &cОшибка&f, вы не привязали свой VK.'
    vk_ok: '&aПроверка данных... &fВаш аккаунт действительно привязан, ваша награда: /vkreward'
  reward:
    reward_vk_null: '&aПроверка данных... &cОшибка&f, сначала привяжите аккаунт. Проверить подвязан ли аккаунт &7- &a/vkcheck'
    reward_vk_ok: '&aВы получили награду в размере 5 коинов.'
    cooldown: '&cНаграду можно получать раз в 10 минут.' # Не работает, используйте любой плагин на задержку команд : (

give-reward:
  commands: # Укажите тут команду для выдачи награды
    - 'say &fИгрок &c%player%&f получил награду.'
  reward_cooldown: 600 # Не работает, используйте любой плагин на задержку команд : (
```

Если вам лень _скомпилировать плагин_, скачайте из релизов.
