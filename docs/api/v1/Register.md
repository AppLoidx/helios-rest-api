**Register**
----
  Получение access_token и refresh_token с помощью логина и пароля

* **URL**

  /api/register

* **Method:**
  
  POST
  
*  **URL Params**

   **Required:**
 
   * `login=[string]`
   * `password=[string]`
   * `first_name=[string]`
   * `last_name=[string]`
   * `email=[string]`


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:**
    ```json
    {
      "error" : "invalid_username",
      "error_description" : "This username already is taken"
    }
    ```

    
    
* **Sample Call:**

  `POST .../api/register?username=Arthur3&password=123&firstName=Arthur&lastName=Kupriyanov&email=apploidyakutsk@gmail.com`

<hr>

* **Method:**
  
  DELETE
  
*  **URL Params**

   **Required:**
 
   * `login=[string]`
   * `password=[string]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** 
    ```json
        {
          "error": "invalid_credentials",
          "error_description": "invalid username or password"
        }
    ```
    
    
* **Sample Call:**

  `POST .../api/register?username=Arthur3&password=123`
  
* **Note**

    * **DELETE** method maybe can move to another resource `[29.07.19]`
    * Password verification will be added soon `[29.07.19]`
  