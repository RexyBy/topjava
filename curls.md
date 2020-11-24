# REST API

The REST API to the topjava app is described below.

## Get users list (admins only)

### Request

`GET /rest/admin/users`

    curl --location --request GET 'localhost:8080/topjava/rest/admin/users'

### Response

    Date: Tue, 24 Nov 2020 14:44:09 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json
    
    [
        {
            "id": 100001,
            "name": "Admin",
            "email": "admin@gmail.com",
            "password": "admin",
            "enabled": true,
            "registered": "2020-11-24T14:44:03.036+00:00",
            "roles": [
                "USER",
                "ADMIN"
            ],
            "caloriesPerDay": 2000,
            "meals": null
        },
        {
            "id": 100000,
            "name": "User",
            "email": "user@yandex.ru",
            "password": "password",
            "enabled": true,
            "registered": "2020-11-24T14:44:03.036+00:00",
            "roles": [
                "USER"
            ],
            "caloriesPerDay": 2000,
            "meals": null
        }
    ]

## Create user (admins only)

### Request

`POST /rest/admin/users`

    curl --location --request POST 'localhost:8080/topjava/rest/admin/users' \
    --header 'Content-Type: application/json' \
    --data-raw '{"name": "New2",
     "email": "new2@yandex.ru",
     "password": "passwordNew",
     "roles": ["USER"]
    }'

### Response

    Date: Tue, 24 Nov 2020 14:44:09 GMT
    Status: 201 Created
    Connection: keep-alive
    Content-Type: application/json
    
    {
        "id": 100011,
        "name": "New2",
        "email": "new2@yandex.ru",
        "password": "passwordNew",
        "enabled": true,
        "registered": "2020-11-24T14:50:17.059+00:00",
        "roles": [
            "USER"
        ],
        "caloriesPerDay": 2000
    }

## Get a specific user (admins only)

### Request

`GET /rest/admin/users/id`

    curl --location --request GET 'localhost:8080/topjava/rest/admin/users/100001'

### Response

    Date: Tue, 24 Nov 2020 14:44:09 GMT
    Status: 200 OK
    Connection: keep-alive
    Content-Type: application/json
    
    {
        "id": 100001,
        "name": "Admin",
        "email": "admin@gmail.com",
        "password": "admin",
        "enabled": true,
        "registered": "2020-11-24T14:44:03.036+00:00",
        "roles": [
            "USER",
            "ADMIN"
        ],
        "caloriesPerDay": 2000,
        "meals": null
    }
    
## Update a specific user (admins only)
    
### Request
    
    `PUT /rest/admin/users/id`
    
        curl --location --request PUT 'localhost:8080/topjava/rest/admin/users/100000' \
        --header 'Content-Type: application/json' \
        --data-raw '{"name": "UserUpdated",
         "email": "user@yandex.ru",
         "password": "passwordNew",
         "roles": ["USER"]
        }'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 204 No Content
        Connection: keep-alive
        
## Delete a specific user (admins only)
    
### Request
    
    `DELETE /rest/admin/users/id`
    
        curl --location --request DELETE 'localhost:8080/topjava/rest/admin/users/100000'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 204 No Content
        Connection: keep-alive
        
## Get user by email
    
### Request
    
    `GET /rest/admin/users/by`
    
        curl --location --request GET 'localhost:8080/topjava/rest/admin/users/by?email=admin@gmail.com'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 200 OK
        Connection: keep-alive
        Content-Type: application/json
        
        {
            "id": 100001,
            "name": "Admin",
            "email": "admin@gmail.com",
            "password": "admin",
            "enabled": true,
            "registered": "2020-11-24T15:14:01.732+00:00",
            "roles": [
                "USER",
                "ADMIN"
            ],
            "caloriesPerDay": 2000,
            "meals": null
        }
  
## Get user by himself
    
### Request
    
    `GET /rest/profile`
    
        curl --location --request GET 'localhost:8080/topjava/rest/profile'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 200 OK
        Connection: keep-alive
        Content-Type: application/json
        
        {
            "id": 100000,
            "name": "User",
            "email": "user@yandex.ru",
            "password": "password",
            "enabled": true,
            "registered": "2020-11-24T15:14:01.732+00:00",
            "roles": [
                "USER"
            ],
            "caloriesPerDay": 2000,
            "meals": null
        }
        
## Delete user by himself
    
### Request
    
    `DELETE /rest/profile`
    
        curl --location --request DELETE 'localhost:8080/topjava/rest/profile'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 204 No Content
        Connection: keep-alive

## Update user by himself
    
### Request
    
    `PUT /rest/profile`
    
        curl --location --request PUT 'localhost:8080/topjava/rest/profile' \
        --header 'Content-Type: application/json' \
        --data-raw '{"name": "New777",
         "email": "new777@yandex.ru",
         "password": "passwordNew",
         "roles": ["USER"]
        }'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 204 No Content
        Connection: keep-alive

## Get meals list
    
### Request
    
    `GET /rest/meals`
    
        curl --location --request GET 'localhost:8080/topjava/rest/meals'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 200 OK
        Connection: keep-alive
        Content-Type: application/json
        
        [
            {
                "id": 100008,
                "dateTime": "2020-01-31T20:00:00",
                "description": "Ужин",
                "calories": 510,
                "excess": true
            },
            {
                "id": 100007,
                "dateTime": "2020-01-31T13:00:00",
                "description": "Обед",
                "calories": 1000,
                "excess": true
            },
            {
                "id": 100006,
                "dateTime": "2020-01-31T10:00:00",
                "description": "Завтрак",
                "calories": 500,
                "excess": true
            },
            {
                "id": 100005,
                "dateTime": "2020-01-31T00:00:00",
                "description": "Еда на граничное значение",
                "calories": 100,
                "excess": true
            },
            {
                "id": 100004,
                "dateTime": "2020-01-30T20:00:00",
                "description": "Ужин",
                "calories": 500,
                "excess": false
            },
            {
                "id": 100003,
                "dateTime": "2020-01-30T13:00:00",
                "description": "Обед",
                "calories": 1000,
                "excess": false
            },
            {
                "id": 100002,
                "dateTime": "2020-01-30T10:00:00",
                "description": "Завтрак",
                "calories": 500,
                "excess": false
            }
        ]

## Get specific meal
    
### Request
    
    `GET /rest/meals/id`
    
        curl --location --request GET 'localhost:8080/topjava/rest/meals/100004'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 200 OK
        Connection: keep-alive
        Content-Type: application/json
        
        {
            "id": 100004,
            "dateTime": "2020-01-30T20:00:00",
            "description": "Ужин",
            "calories": 500,
            "user": {
                "id": 100000
            }
        }

## Delete specific meal
    
### Request
    
    `DELETE /rest/meals/id`
    
        curl --location --request DELETE 'localhost:8080/topjava/rest/meals/100004'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 204 No Content
        Connection: keep-alive

## Create meal
    
### Request
    
    `POST /rest/meals`
    
        curl --location --request POST 'localhost:8080/topjava/rest/meals' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "dateTime": "2020-11-22T16:00:00",
            "description": "Еще один поздний обед",
            "calories": 500
        }'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 201 Created
        Connection: keep-alive
        Content-Type: application/json
        
        {
            "id": 100011,
            "dateTime": "2020-11-22T16:00:00",
            "description": "Еще один поздний обед",
            "calories": 500,
            "user": {
                "id": 100000
            }
        }

## Update specific meal
    
### Request
    
    `PUT /rest/meals/id`
    
        curl --location --request PUT 'localhost:8080/topjava/rest/meals/100004' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "dateTime": "2020-11-22T16:00:00",
            "description": "Обновленная еда",
            "calories": 500
        }'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 204 No Content
        Connection: keep-alive

## Get meals between date and time
    
### Request
    
    `GET /rest/meals/filter`
    
        curl --location --request GET 'localhost:8080/topjava/rest/meals/filter?startTime=11:00:00&endTime=21:00:00&startDate=2020-01-30&endDate=2020-01-30'
    
### Response
    
        Date: Tue, 24 Nov 2020 14:44:09 GMT
        Status: 200 OK
        Connection: keep-alive
        Content-Type: application/json
        
        [
            {
                "id": 100004,
                "dateTime": "2020-01-30T20:00:00",
                "description": "Ужин",
                "calories": 500,
                "excess": false
            },
            {
                "id": 100003,
                "dateTime": "2020-01-30T13:00:00",
                "description": "Обед",
                "calories": 1000,
                "excess": false
            }
        ]
