
## Moved to Spring Ver: https://github.com/AppLoidx/helios-rest-api-spring
<br>
<br>
<br>
<h1 align=center>Helios REST API side
<br>
  <img src="https://codecov.io/gh/AppLoidx/helios-rest-api/branch/master/graph/badge.svg" />
  <a href="https://travis-ci.org/AppLoidx/helios-rest-api"><img src="https://travis-ci.org/AppLoidx/helios-rest-api.svg?branch=master" /></a>
  <img src="https://img.shields.io/badge/Java-1.8-brightgreen" />
</h1>



## 🙂Author

**Arthur Kupriyanov**

- VK: [@apploidxxx](https://vk.com/apploidxxx)
- Github: [@AppLoidx](https://github.com/AppLoidx)
- Mail: [apploidyakutsk@gmail.com](apploidyakutsk@gmail.com)

# 📙Документация к API Helios

<h2 align=center><img src="https://i.imgur.com/LCermq5.png" width=170 height=170 /></h2>

**API docs v1** : [Веб-версия](https://apploidx.github.io/helios-doc/) идентичная с README.md

## Запуск
Перед запуском необходимо настроить `hiberante.cfg.xml`
```
mvn install
java -jar target/fileName.jar
```
## Общие сведения
### Предназначение
Этот API должен быть доступным для пользования всеми разработчиками, в том числе для создания собственных сервисов, интеграции с социальными сетями и тому подобным. Также следовательно он должен иметь методы аутентификации и идентификации пользователей через протокол OAuth.

Документацию по использованию API и примеры можно найти [здесь](https://apploidx.github.io/helios-doc/)

### Средства разработки
Реализован **JAX-RS** через **Jersey** и поднят на сервере **GrizzlyHttpServer** от Apache. Задеплоен в сервисе [heroku](https://heroku.com)

Сборка производится через **maven** с системой автоматической сборки на [travis-ci](https://travis-ci.org/AppLoidx/helios-rest-api)

## Иерархия файлов
`com.apploidxxx` :
* `api` - классы и методы отвечающие за обработку запросов
	* `exceptions` - пользовательские исключения
	* `filter` - фильтры запросов
	* `model` - POJO объекты
* `ds` - средства инициализации datasource (HibernateSession)
* `entity` - хранимые объекты (persistence units)
	* `dao` - расположение всех DAO и их сервисов

`test`:
* `api` - тесты по API (запросы через rest-assured)
* `entity` - тесты по логике работы хранимых объектов

`resources/static`:
* `external` - формы для авторизации\регистрации через OAuth

## api/exceptions
![](https://i.imgur.com/oGFyG4C.png) 
#### IResponsibleException
- Интерфейс для всех исключений выбрасываемых в работе с API и которые могут генерировать `Response` для отправки клиенту. 
```java
import javax.ws.rs.core.Response;
public interface IResponsibleException {  
	  Response getResponse();  
	  String getErrorMessage();  
	  String getErrorDescription();  
}
```
#### ResponsibleException
- Прослойка между интерфейсом `IResponsibleException` и самими реализациями исключений, которая наследуется от `Exception` и является абстрактной:
```java
public abstract class ResponsibleException extends Exception implements IResponsibleException {  }
```
Наследование исключения от этого класса (его реализация `IResponsibleException`) дает нам возможность использовать (вместе с `try/catch` конструкциями) такого вида упрощения обработки ошибок:

_Листинг 1.1 QueueApi_
```java
public Response getQueue(   @Valid@NotNull@QueryParam("queue_name") String queueName){  
  
  try {  
	Queue q = QueueManager.getQueue(queueName);
	return Response.ok(q).build();  
  } catch (InvalidQueueException e) {  
        return e.getResponse();  
  }  
}
```
Таким образом, исключение умеет генерировать `Response`, который можно возвращать пользователю. Вот его реализация:

_Листинг 1.2 InvalidQueueException_
```java
public class InvalidQueueException extends ResponsibleException{  
  
    @Override  
  public Response getResponse() {  
        return Response  
                .status(Response.Status.NOT_FOUND)  
                .entity(new ErrorMessage(getErrorMessage(), getErrorDescription()))  
                .build();  
  }  
  
    @Override  
  public String getErrorMessage() {  
        return "invalid_queue";  
  }  
  
    @Override  
  public String getErrorDescription() {  
        return "queue not found";  
  }  
}
```
## api/model
Здесь представлены POJO объекты, которые по назначению должны сериализовываться в json. Все имена полей я буду приводить как они написаны в самом коде (то есть в верблюжем стиле), но все они при сериализации меняют свой проперти на стиль json, то есть `this_is_my_value`
### ErrorMessage
Объект являющийся стандартным для отправки ответа запросам, которые вызывали ошибку или были некорректны. Содержит поля `error` и `errorDescription`
### Check
Объект содержащий поле `exist`. Обычно используется для `CheckApi`, например, для проверки существования такого имени пользователя или очереди.
### UserInfo
Класс-враппер для сущности User. Вот его поля:
```java
private User user;  
private List<String[]> queues; 
 
@JsonProperty("queues_member")    
private List<String[]> queuesMember;  

@JsonProperty("swap_requests")  
private List<Map<String, String>> swapRequests;
```
* **queues** - коллекция из массива очередей, содержащих два значения - короткое имя (ссылка) и полное имя очереди. Например, `{["shortLink","My Queue Fullname" ]}`. Этот список содержит очереди, в которых пользователь находится как участник или администратор.
* **queuesMember** - такой же список как `queues`, но содержит очереди, в которых пользователь является лишь участником (не входят очереди, в которых он администратор)
* **user** - данные о пользователе, кроме приватных данных, как например, пароль
* **swapRequests** - список мапов описывающих заявки на обмен местами (пока только исходящих). Метод добавляющий значение в `swapRequest`:<br>
_Листинг 2.1 UserInfo_
	```java
	private void addSwapRequest(Queue queue, User user){  
	  SwapContainer sc = queue.getSwapContainer();  
	  User target = sc.getSwapRequest(user);  
	  if (target != null) {  
	          Map<String, String> hashMap = new HashMap<>();  
			  hashMap.put("queue_name", queue.getName());  
			  hashMap.put("queue_fullname", queue.getFullname());  
			  hashMap.put("target_username", target.getUsername());  
			  hashMap.put("target_firstname", target.getFirstName());  
			  hashMap.put("target_lastname", target.getLastName());  
			  swapRequests.add(hashMap);  
	  }  
	}
	```
## api/util

### ErrorResponseFactory
Реализация паттерна фабрики - имеет статические методы возвращающие объекты `Response` с тем или иным кодом ошибки и как говорилось ранее, объектом `ErrorMessage` в качестве ответа пользователю:

_Листинг 2.2 ErrorResponseFactory_
```java
public abstract class ErrorResponseFactory {  
    public static Response getInvalidParamErrorResponse(String description){  
        return Response  
                .status(Response.Status.BAD_REQUEST)  
                .entity(new ErrorMessage("invalid_param", description))  
                .build();  
  }  
  
    public static Response getForbiddenErrorResponse(String description){  
        return Response  
                .status(Response.Status.FORBIDDEN)  
                .entity(new ErrorMessage("insufficient_rights", description))  
                .build();  
  }  
  
    public static Response getForbiddenErrorResponse(){  
        return getForbiddenErrorResponse("You don't have enough rights");  
  }  
  ...
}
```

### Password
Класс для проверки и валидации паролей. Использует MD5 хэширование. Имеет два публичных метода:

_Листинг 2.3 Password_
```java
public static String hash(String password){  
    return Md5Crypt.md5Crypt((password + salt).getBytes());  
}
public static boolean isEqual(String rawPassword, String hashedPassword){  
    return hashedPassword.equals(hash(rawPassword, hashedPassword));  
}
```
Собственно, `hash` хэширует пароль для хранения в БД, а `isEqual` проверяет совпадение паролей.

### QueueManager, UserManager, UserSessionManager
Это обертки над `QueueService` и `UserService`, которые работают с сущностями БД. Они имеют методы, которые могут выкидывать специфичные исключение, как например, `InvalidQueueException` или `UserNotFoundException`.

_Листинг 2.4 QueueManager_
```java
public class QueueManager {  
  public  static Queue getQueue(String queueName) throws InvalidQueueException {  
      Queue q = QueueService.findQueue(queueName);  
	  if (q == null){  
	      throw new InvalidQueueException();  
	  } else {  
	      return q;  
	  }  
  }  
}
```

### VulnerabilityChecker
Проверяет строковые данные на уязвимости и наличие опасных значений, таких как `<script>`. Имеет один метод `checkWord`, который в случае опасности выбрасывает исключение `VulnerabilityException`.

## entity/dao

В этом пакете находятся DAO-объекты, при этом каждого такого объекта область видимости ограничивается лишь пакетом, а публичный доступ дается через сервисный класс (подробнее далее)

### DAOBasicOperations
Класс который реализует простые операции с сущностями и БД, чтобы не писать много boilerplate-кода он был вынесен в отдельный класс и используется с обобщениями:
```java
public class DAOBasicOperations<T> {  
    public void save(T obj) {  
      Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();  
	  Transaction tx1 = session.beginTransaction();  
	  session.save(obj);  
	  tx1.commit();  
	  session.close();  
  }  
  
    public void update(T obj) {  
      Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();  
	  Transaction tx1 = session.beginTransaction();  
	  session.update(obj);  
	  tx1.commit();  
	  session.close();  
  }  
  
    public void delete(T obj) {  
	  Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();  
	  Transaction tx1 = session.beginTransaction();  
	  session.delete(obj);  
	  tx1.commit();  
	  session.close();  
	}  
}
```
Рассмотрим его использование на примере:

_Листинг 3.1 MessageDAO_
```java
public class MessageDAO {  
      private DAOBasicOperations<Message> basicOperations = new DAOBasicOperations<>();  
	  Message findById(Long id) {  
	        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Message.class, id);  
	  }  
  
	  void save(Message user) {  
	        basicOperations.save(user);  
	  }  
  
    void update(Message user) {  
	        basicOperations.update(user);  
    }  
  
    void delete(Message user) {  
	        basicOperations.delete(user);  
    }  
    List<Message> findAll() {  
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){  
            return session.createQuery("from Message", Message.class).list();  
	    }  
    }  
}
```
## entity
|Сущность| Назначение |
|--|--|
|User| Данные о пользователе |
| ContactDetails | Дополнительные данные о пользователе (email, vk id ...) |
|Chat| Чат очереди (one-to-one `Queue`) |
| Message | Сообщение в чате (one-to-one `Chat`) |
|Queue| Информация об очереди |
| Session | Сессия с пользователем (accessToken, refreshToken) |




