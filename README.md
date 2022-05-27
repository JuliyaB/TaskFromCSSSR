Тестовое задание (для AUTO QA BACK-END)
Специально для тебя мы подготовили REST (https://superhero.qa-test.csssr.com/swagger-ui.html#/) и
SOAP (https://soap.qa-test.csssr.com/ws/soap.wsdl) сервисы.
Необходимо автоматизировать проверки. Ссылку на репозиторий с кодом нужно оставить в поле для комментария. Проверь, что
доступ открыт для нас. При желании можно оформить найденные баги. При выполнении задания используй все свои знания, опыт
и смекалку.

PS: тесты можно было автоматизировать так же с вынесением запросов в отдельные методы.

Баги:

1) Значение телефона null (post/put)

   При отправки post запроса (https://superhero.qa-test.csssr.com/superheroes)
   
   с body {
   
   "birthDate": "2019-02-21",
   
   "city": "New York",
   
   "fullName": "Doctor Strange",
   
   "gender": "F",
   
   "mainSkill": "Magic",
   
   "phone": "+74998884433"
   
   }
   
   В ответе приходит body {
   
   "id": 111,
   
   "fullName": "Doctor Strange",
   
   "birthDate": "2019-02-21",
   
   "city": "New York",
   
   "mainSkill": "Magic",
   
   "gender": "F",
   
   "phone": null
   
   }
   
   Ожидалось:
   
   {
   
   "id": 111,
   
   "fullName": "Doctor Strange",
   
   "birthDate": "2019-02-21",
   
   "city": "New York",
   
   "mainSkill": "Magic",
   
   "gender": "F",
   
   "phone": "+74998884433"
   
   }
   
2) Поле Gender звписывает любые значения (post/put)

   При отправки post запроса (https://superhero.qa-test.csssr.com/superheroes)
   
   с body {
   
   "birthDate": "2019-02-21",
   
   "city": "New York",
   
   "fullName": "Doctor Strange",
   
   "gender": "vdbdr11",
   
   "mainSkill": "Magic",
   
   "phone": "+74998884433"
   
   }
   
   В ответе приходит body {
   
   "id": 112,
   
   "fullName": "Doctor Strange",
   
   "birthDate": "2019-02-21",
   
   "city": "New York",
   
   "mainSkill": "Magic",
   
   "gender": "vdbdr11",
   
   "phone": null
   
   }
   
   Ожидалось: ошибка

3) city принимает пустую string (post/put)
   
   При отправки post запроса (https://superhero.qa-test.csssr.com/superheroes)
   
   с body {
   
   "birthDate": "2019-02-21",
   
   "city": "",
   
   "fullName": "Doctor Strange",
   
   "gender": "F",
   
   "mainSkill": "Magic"
   
   }
   
   В ответе приходит body {
   
   "id": 113,
   
   "fullName": "Doctor Strange",
   
   "birthDate": "2019-02-21",
   
   "city": "",
   
   "mainSkill": "Magic",
   
   "gender": "F",
   
   "phone": null
   
   }
   
   Ожидалось: ошибка

4) При неверном url/id возвращает весь список
   
   Отправляем get (https://superhero.qa-test.csssr.com/superheroes/?id=1)
   
   В ответ приходит весь список супергероев
   
   Ожидалось: ошибка

5) Возращается разный список супергероев
   
   Отправляем get (https://superhero.qa-test.csssr.com/superheroes/)
   
   В ответ приходит один список
   
   Повторно отправляем get (https://superhero.qa-test.csssr.com/superheroes/)
   
   В ответ приходит другой список
   
   PS: есть данные которые возвращаются стабильно
   
   Ожидалось: в обоих случаях должен приходить один и тот же список
   

6) При удалении несуществуещго id код ответа 200
   
   Отправляем delete (http://superhero.qa-test.csssr.com/superheroes/930)
   
   В ответ приходит код ответа 200
   
   Ожидалось: ошибка
