**User**
----
  Получение данных о пользователе. Возвращает сущности [User](../../../src/main/java/com/apploidxxx/entity/User.java) и 
  [ContactDetails](../../../src/main/java/com/apploidxxx/entity/ContactDetails.java)


* **URL**

  /api/user

* **Method:**
  
  GET
  
*  **URL Params**

   **Required:**
 
   * `access_token=[string]`


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```json
    {
      "queues": [],
      "user": {
        "contact_details": {
          "email": "apploidyakutsk@gmail.com",
          "vkontakteId": 0
        },
        "first_name": "Arthur",
        "id": 62,
        "last_name": "Kupriyanov",
        "username": "Arthur2"
      }
    }
    ```
 
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** 
    ```json
    {
      "error": "invalid_token",
      "error_description": "Your token is invalid. Take authorization and refresh tokens"
    }
    ```
    
    
* **Sample Call:**

  `GET .../api/user?access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=`

* **Note**

    * Update request to request with response having queue list <br>`[23.07.2019]`
  