**Queue**
----
  Содержит 4 метода для работы с очередью

* **URL**

  /api/queue

* **Method:**

  `POST` - создание очереди
  
*  **URL Params**

   **Required:**
 
   `access_token=[string]`<br>
   `queue_name=[string]`<br>
   `fullname=[string]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**
  * **Code:** 400 BAD_REQUEST <br />
    **Content:** `<Response body is empty>`

* **Sample Call:**

  ```
    POST .../api/queue?access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=&fullname=kek&queue_name=lol
  ```
  
 
