**Swap**
----
  Обмен мест. Если оба пользователя взаимно отправляют запрос на смену мест в очереди, то они меняются местами в очереди, при этом следует учитывать, что люди с разными приоритетами - не могут меняться в очереди местами.

* **URL**

  /api/swap

* **Method:**
  
  POST
  
*  **URL Params**

   **Required:**
 
   * `access_token=[string]`
   * `target=[string]` - имя пользователя (цели)
   * `queue_name=[string]`


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**

  * **Code:** 404 Not Found <br />
    **Content:** 
    ```json
    {
      "error": "user_not_found",
      "error_description": "User not found in requested queue"
    }
    ```
  * **Code:** 400 Bad Request<br />
      **Content:** `<Response body is empty>`
    
* **Sample Call:**

  `POST .../api/swap?queue_name=123&target=2&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0`

* **Note:**
Нужно обновить API так, чтобы люди с разными приоритетами не могли меняться.

<h5 align=center><a href="/helios-doc/wiki/api">Назад</a> | <a href="helios-doc/wiki">На главную</a></h5>
