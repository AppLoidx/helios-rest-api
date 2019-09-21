# OAuth 2.0 в Helios API

Перед прочтением советую ознакомиться с [протоколом OAuth](AboutTokensRU.md)

## Получение `authorization_code`
Для начала вам необходимо перенаправить браузер пользователя по адресу:
```text
https://helios-service.herokuapp.com/html/external/login.html?redirect_uri=<YOUR_VALUE>
```

где вместо `<YOUR_VALUE>` вы должны поставить страницу, на которую будет перенаправлен пользователь с 
хэш-данным `authorization_code`.

Если вы не укажите параметр `redirect_uri`, то пользователь по-умолчанию будет перенаправлен на страницу
`https://helios-service.herokuapp.com/html/external/blank.html`, содержащий `authorization_code`

## Получение токенов доступа
Чтобы получить `access_token` и `refresh_token`, вам небоходимо сделать запрос на ендпоинт:
```text
https://helios-service.herokuapp.com/api/oauth?authorization_code=<YOUR_CODE>
```
где вместо `<YOUR_CODE>` вы должны использовать код, который вы получили на первом пункте.

В ответе вы получите `access_token` и `refresh_token`:
```json5
 {
     "access_token": "QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQ5NDg=",
     "refresh_token": "refresh-token"
 }
```

## Example

Допустим, у нас есть веб-приложение, которое имеет ендпоинт по адреcу:
```text
https://example.com/login
```

Для начала мы перенаправим браузер пользователя на адрес:
```text
https://helios-service.herokuapp.com/html/external/login.html?redirect_uri=https%3A%2F%2Fexample.com%2Flogin
```

_Прим. при URL-кодировании: https://example.com/login превращается в https%3A%2F%2Fexample.com%2Flogin_
<hr>

Там пользователь проходит авторизацию и если она успешна, то браузер пользователя перенаправляется по адресу:
```text
https://example.com/login#WFub3YtMTQ0NTU0MDM
```
где `#WFub3YtMTQ0NTU0MDM` это код авторизации, который нам понадобится для получения `access_token` и `refresh_token`.

Далее, считываем эти хэш-данные и отправляем новый запрос:
```text
https://helios-service.herokuapp.com/api/oauth?authorization_code=WFub3YtMTQ0NTU0MDM
```

И получаем свои заветные токены доступа:
```json5
 {
     "access_token": "VyMU0MDM3NXkt1cHJpeWFubQXJ0aH3YtMTQ5ND0NTNhbHQg=",
     "refresh_token": "MTQ5NDWFubyMU0NTNhbHQ0MDM3n"
 }
```
