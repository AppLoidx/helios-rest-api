**User**
----
  Получение данных о пользователе. Возвращает сущности [User](https://github.com/AppLoidx/helios-rest-api/blob/master/src/main/java/com/apploidxxx/entity/User.java) и 
  [ContactDetails](https://github.com/AppLoidx/helios-rest-api/blob/master/src/main/java/com/apploidxxx/entity/ContactDetails.java)


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
   "queues":[
      {
         "short_name":"123",
         "fullname":"123"
      }
   ],
   "queues_member":[
      {
         "short_name":"123",
         "fullname":"123"
      }
   ],
   "swap_requests_in":[
      {
         "queue_name":"777qty",
         "firstname":"Sasha",
         "lastname":"Tarasov",
         "queue_fullname":"123",
         "username":"trsv"
      }
   ],
   "swap_requests_out":[
      {
         "queue_name":"123",
         "firstname":"Arthur",
         "lastname":"Kupriyanov",
         "queue_fullname":"123",
         "username":"2"
      }
   ],
   "user":{
      "contact_details":{
         "email":"apploidyakutsk@gmail.com",
         "img":"https://imgur.com/giuTlrm",
         "vkontakteId": 0
      },
      "first_name":"Arthur",
      "id":10587,
      "last_name":"Kupriyanov",
      "user_type":"STUDENT",
      "username":"1"
   }
}
```
* * **queues** - все очереди участника (в которых он является участником и в тех которых он администратор)
*	* **queues_member** - только те очереди, в которых пользователь находится как участник (это необходимое и достаточное условие)
*	* **user_type** - тип участника:
		 * STUDENT  - студент
		 * TEACHER - преподаватель
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

<hr>

* **Method:**
  
  PUT - изменение настроек профиля
  
*  **URL Params**

   **Required:**
 
   * `access_token=[string]`
   * `param=[string]` - имя изменяемого параметра
	   * group - изменение имени группы
   * `value` - новое значение параметра


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `<Response body is empty>`
    
* **Error Response:**

  * **Code:** 400 BAD_REQUEST <br />
    **Content:** 
    ```json
    {
      "error": "invalid_param",
      "error_description": "This param name not found"
    }
    ```
    ```json
    {
      "error": "invalid_param",
      "error_description": "Invalid group name"
    }
    ```

* **Sample Call:**

  `PUT .../api/user?param=group&value=P3112&access_token=QXJ0aHVyMkt1cHJpeWFub3YtMTQ0NTU0MDM3NXNhbHQyNzY=`
  

