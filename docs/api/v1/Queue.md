**Queue**
----
  Содержит 4 метода для работы с очередью

* **URL**

  /api/queue

* **Method:**

  `POST` - создание очереди
  
*  **URL Params**

   **Required:**
 
   * `access_token=[string]`
   * `queue_name=[string]`
   * `fullname=[string]`
   
   **Optional:**
   
   * `generation=[string]` - тип гереации при автогенерации очередей
        * `one_week` - генерировать каждую неделю
        * `two_week` - генерировать каждые две недели
        * Если не указывать, то очерелдь не будет генерироваться сама
   * `password=[string]` - указывается при создании закрытой очереди
        * Если не указать, то очередь будет открытой
   
   

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**
  * **Code:** 400 BAD_REQUEST <br />
    **Content:** `<Response body is empty>`

* **Sample Call:**

  ```
    POST .../api/queue?fullname=kek&queue_name=lol&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=
  ```
  
* **Method:**

  `PUT` - создание очереди
  
*  **URL Params**

   **Required:**
 
   * `access_token=[string]`
   * `queue_name=[string]`
   
   **Optional:**
   
   * `password=[string]` - указывается, если очередь закрытая
   

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**
  * **Code:** 400 BAD_REQUEST <br />
    **Content:** `<Response body is empty>`

* **Sample Call:**

  ```
    POST .../api/queue?queue_name=lol&password=123&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=
  ```
  
 
