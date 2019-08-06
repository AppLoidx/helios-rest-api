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

<hr>

* **Method:**

  `PUT` - присоединение к очереди
  
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
    PUT .../api/queue?queue_name=lol&password=123&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=
  ```
  
<hr>

* **Method:**

  `GET` - создание очереди
  
*  **URL Params**

   **Required:**

   * `queue_name=[string]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```json
    {
      "creation_date":"2019-08-06T05:45:50.318Z[UTC]",
      "formattedDate":"Вт, 6 авг 14:45",
      "fullname":"Test%20Queue",
      "members":
        [
          {
            "contactDetails":
                { "email":"apploidyakutsk@gmail.com",
                  "vkontakteId":0},
            "firstName":"Arthur",
            "id":1398,
            "lastName":"Kupriyanov",
            "user_type":"STUDENT",
            "username":"123"
          },
          {
            "contactDetails":
              { "email":"apploidyakutsk@gmail.com",
                "vkontakteId":0},
            "firstName":"Arthur",
            "id":1402,
            "lastName":"Kupriyanov",
            "user_type":"STUDENT",
            "username":"wrong-user"}],
      "name":"testQue",
      "queue_sequence":[1398,1402],
      "super_users":
        [
          {
            "contactDetails":
              { "email":"apploidyakutsk@gmail.com",
                "vkontakteId":0},
            "firstName":"Arthur",
            "id":1407,
            "lastName":"Kupriyanov",
            "user_type":"STUDENT",
            "username":"123"
          }
        ]
      
    }
    ```
 
* **Error Response:**
  * **Code:** 400 BAD_REQUEST <br />
    **Content:** `<Response body is empty>`

* **Sample Call:**

  ```
    POST .../api/queue?queue_name=lol&password=123&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=
  ```
 
