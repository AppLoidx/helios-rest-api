**Check**
----
  Используется для проверки данных: существующие очереди, занятые имена пользователей и тд.

* **URL**

  /api/check

* **Method:**
  
  GET
  
*  **URL Params**

   **Required:**
 
   * `check=[string]`
	   * user_exist - существование пользователя
	   * queue_exist - существование очереди
	   * queue_match - поиск очередей по фрагменту
	   * queue_private - проверка приватности очереди
	   * email_exist - проверка зарегистрирован ли пользователь с такой почтой

	**Optional:**
	* `username=[string]` - используется в качестве параметра для методов check: _user__**
	* `queue_name=[string]` - используется в качестве параметра для методов check: _queue__**
	* `email=[string]` - используется в качестве параметра для методов: _email__**

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```json
     {
         exist: true
     }
     ```
     ```json
	[  
		[  
				"shortName",  
				"Queue fullname"  
		],  
		[  
				"anotherQueue",  
				"Fullname"  
		]  
	]
	 ```
 
* **Error Response:**
    
  * **Code:** 400 Bad Request<br />
      **Content:** `<Response body is empty>`
    
* **Sample Call:**

  `GET .../api/check?username=AppLoidx&check=user_exist`

<h5 align=center><a href="/helios-doc/wiki/api">Назад</a> | <a href="helios-doc/wiki">На главную</a></h5>
  
