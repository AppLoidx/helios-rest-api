**Chat**
----
  Api для управления чатом и получения сообщений

* **URL**

  /api/chat

* **Method:**
  
  GET
  
*  **URL Params**

   **Required:**
 
    * `queue_name=[string]`
    * `last_msg_id=[int]` - id сообщения, с которого нужно получить новые

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 

    ```json
    [
      { 
        "fullname":"Arthur Kupriyanov",
        "id":127,"message":"It's me",
        "username":"123"},
    
      { "fullname":"Arthur Kupriyanov",
        "id":126,
        "message":"Hi",
        "username":"123"},
    
      { 
        "fullname":"Arthur Kupriyanov",
        "id":128,
        "message":"Yep! I joined to queue",
        "username":"123"},
    
      { 
        "fullname":"Arthur Kupriyanov",
        "id":129,
        "message":"I'm fine now",
        "username":"123",
      }
    ]

    ```
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** 
    
    
* **Sample Call:**

  `GET .../api/user?access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=`

* **Note**

    * Update request to request with response having queue list <br>`[23.07.2019]`
  
