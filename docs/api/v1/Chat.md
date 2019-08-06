**Chat**
----
  Api для управления чатом и получения сообщений

* **URL**

  /api/chat/{queueName}

* **Method:**
  
  `GET` - получить сообщения
  
*  **URL Params**

   **Required:**
 
    * `last_msg_id=[int]` - id сообщения, с которого нужно получить новые

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 

    ```json
    [
      { 
        "fullname":"Akari Hayasaka",
        "id":127,"message":"It's me",
        "username":"123"},
    
      { "fullname":"Arthur Kupriyanov",
        "id":126,
        "message":"Hi",
        "username":"133"},
    
      { 
        "fullname":"Akari Hayasaka",
        "id":128,
        "message":"Yep! I joined to queue",
        "username":"133"},
    
      { 
        "fullname":"Arthur Kupriyanov",
        "id":129,
        "message":"I'm fine now",
        "username":"123"
      }
    ]

    ```
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** `<Response body is empty>`
    
    
* **Sample Call:**

  `GET .../api/chat/testQueue?last_msg_id=12`

<hr>

* **Method:**
  
  `PUT` - добавить сообщение
  
*  **URL Params**

   **Required:**
 
    * `message=[string]` - сообщение, которое нужно отправить
    * `access_token=[string]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
    
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** `<Response body is empty>`
    
    
* **Sample Call:**

  `GET .../api/chat/testQueue?message=myMessage&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=`

  
