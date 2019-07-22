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
    **Content:** `<Response body is empty>`
    
    
* **Sample Call:**

  `POST .../api/register?username=Arthur3&password=123&firstName=Arthur&lastName=Kupriyanov&email=apploidyakutsk@gmail.com`

* **Note**

    * Error response can be changed <br>`[23.07.2019]`
  