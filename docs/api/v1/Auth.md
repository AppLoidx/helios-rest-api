**Auth**
----
  Получение access_token и refresh_token с помощью логина и пароля

* **URL**

  /api/auth

* **Method:**
  
  GET
  
*  **URL Params**

   **Required:**
 
   * `login=[string]`
   * `password=[string]`


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```json
     {
         "access_token": "QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQ5NDg=",
         "refresh_token": "refresh-token"
     }
     ```
 
* **Error Response:**

  * **Code:** 401 Unauthorized <br />
    **Content:** 
    ```json
    {
      "error": "invalid_credentials",
      "error_description": "invalid username or password"
    }
    ```
  * **Code:** 400 Bad Request<br />
      **Content:** `<Response body is empty>`
    
* **Sample Call:**

  `GET .../api/auth?username=Arthur2&password=123`


  