**QueueControl**
----
  Управление настройками очереди

* **URL**

  /api/queue/{queueName}

* **Method:**
  
  PUT
  
*  **URL Params**

   **Required:**
 
   * `access_token=[string]`
   * `action=[string]`
        * `shuffle`     - перемещать очередь
        * `settype`     - изменить тип очереди (генерации) (required: `type`)
        * `setadmin`    - назначить администратора (required: `admin`)
   
   **Optional:**
   
   * `type=[string]`    - тип генерации ("one_week", "two_week")
   * `admin=[string]`   - username


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
 
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** 
    ```json
    {
      "error": "invalid_param",
      "error_description": "Queue with name P3112 not found"
    }
    ```
  * **Code:** 404 NOT_FOUND <br/>
    **Content:**
    ```json
    {
      "error": "queue_not_found",
      "error_description": "Invalid action param. Please, check allowed actions"
    }
    ```
    
    
* **Sample Call:**

  `GET .../api/queue/P3112?action=setadmin&admin=nyanCat&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=`

  