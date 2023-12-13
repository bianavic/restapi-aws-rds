# REST API WITH HATEOAS AND RDS AWS

### tools:
- linux
- intellij
- spring data
- spring web
- spring hateoas
- rds aws
- postgresql

## Employee

| API ROUTE		                       | DESCRIPTION                | STATUS |
|:----------------------------------|:---------------------------|:-------|
| [POST] /employees/add             | Add a new employees        | 201    |
| [GET] /employees/employee/{id}    | Retrieve an employee by ID | 200    |
| [GET] /employees	                 | Retrieve all the employees   | 200    |
| [PUT] /employees	                 | Update an user by ID           | 200    |
| [DELETE] /employees/employee/{id} | Delete an user by ID      | 204    |


#### add employee
```bash
curl --location 'http://localhost:8001/employees/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Joao Carlos",
    "role": "Thief",
    "address": "Rua Test 51"
}'
```
###### 201 CREATED
``` json
{
    "id": 4,
    "name": "Joao Carlos",
    "role": "Thief",
    "address": "Rua Test 51"
}
```

#### get all employees
```bash
curl --location 'http://localhost:8001/employees/all'
```
###### 200 OK
``` json
[
    {
        "id": 1,
        "name": "Maria Silva",
        "role": "Chef",
        "address": "avenida silveira dutra 1002",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/employees/employee/1"
            }
        ]
    },
    {
        "id": 2,
        "name": "John Dutra",
        "role": "Mecanico",
        "address": "rua joao freire 231",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/employees/employee/2"
            }
        ]
    },
    {
        "id": 3,
        "name": "Bilbo Baggins",
        "role": "thief",
        "address": "The shine",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/employees/employee/3"
            }
        ]
    },
    {
        "id": 4,
        "name": "Joao Carlos",
        "role": "Thief",
        "address": "Rua Test 51",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/employees/employee/4"
            }
        ]
    }
]
```

#### get an employee by ID
```bash
curl --location 'http://localhost:8001/employees/employee/2'
```
###### 200 OK
``` json
{
    "id": 2,
    "name": "John Dutra",
    "role": "Mecanico",
    "address": "rua joao freire 231",
    "_links": {
        "Employee list": {
            "href": "http://localhost:8001/employees/all"
        },
        "self": {
            "href": "http://localhost:8001/employees/employee/2"
        }
    }
}
```

#### update an employee by ID
```bash
curl --location --request PUT 'http://localhost:8001/employees/update/4' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Uodate Joao Carlos",
    "role": "Teacher",
    "address": "Rua Test 51"
}'
```
###### 200 OK
``` json
{
    "id": 5,
    "name": "Uodate Joao Carlos",
    "role": "Teacher",
    "address": "Rua Test 51"
}
```

#### delete an employee by ID
```bash
curl --location --request DELETE 'http://localhost:8001/employees/employee/4'
```
###### 204 NO CONTENT
``` json

```

## Order

| API ROUTE		                      | DESCRIPTION             | STATUS |
|:---------------------------------|:------------------------|:-------|
| [POST] /orders/add               | Add a new order         | 201    |
| [GET] /orders/order/{id}         | Retrieve an order by ID | 200    |
| [GET] /orders	                   | Retrieve all the orders | 200    |
| [PUT] /orders	                   | Update an order by ID    | 200    |
| [DELETE] /orders/employee/{id}   | Delete an order by ID    | 204    |

#### add an order
```bash
curl --location 'http://localhost:8001/orders/add' \
--header 'Content-Type: application/json' \
--data '{
    "status": "IN_PROGRESS",
    "description": "review"
}'
```
###### 201 CREATED
``` json
{
    "id": 4,
    "status": "IN_PROGRESS",
    "description": "review"
}
```

#### get all orders
```bash
curl --location 'http://localhost:8001/orders/all'
```
###### 200 OK
``` json
[
    {
        "id": 1,
        "status": "COMPLETED",
        "description": "review",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/orders/order/1"
            },
            {
                "rel": "Customer order list",
                "href": "http://localhost:8001/orders/all"
            }
        ]
    },
    {
        "id": 2,
        "status": "IN_PROGRESS",
        "description": "travel",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/orders/order/2"
            },
            {
                "rel": "Customer order list",
                "href": "http://localhost:8001/orders/all"
            }
        ]
    },
    {
        "id": 3,
        "status": "IN_PROGRESS",
        "description": "sale",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/orders/order/3"
            },
            {
                "rel": "Customer order list",
                "href": "http://localhost:8001/orders/all"
            }
        ]
    }
]
```

#### get an order by ID
```bash
curl --location 'http://localhost:8001/orders/order/1'
```
###### 200 OK
``` json
{
    "id": 1,
    "status": "COMPLETED",
    "description": "review",
    "_links": {
        "All Orders": {
            "href": "http://localhost:8001/orders/all"
        },
        "self": {
            "href": "http://localhost:8001/orders/order/1"
        }
    }
}
```

#### update an order by ID
```bash
curl --location --request PUT 'http://localhost:8001/orders/update/4' \
--header 'Content-Type: application/json' \
--data '{
    "id": 4,
    "status": "CANCELLED",
    "description": "review"
}'
```
###### 200 OK
``` json
{
    "id": 4,
    "status": "CANCELLED",
    "description": "review"
}
```

#### update an order by ID
```bash
```
###### 200 OK
``` json

```

#### delete an order by ID
```bash
curl --location --request DELETE 'http://localhost:8001/orders/order/4'
```
###### 204 NO CONTENT
``` json

```


#### Employee Database

| id | address | name | role |
| :--- | :--- | :--- | :--- |
| 1 | avenida silveira dutra 1002 | Maria Silva | Chef |
| 2 | rua joao freire 231 | John Dutra | Mecanico |
| 3 | The shine | Bilbo Baggins | thief |

#### Order Database

| status | id | description |
| :--- | :--- | :--- |
| 1 | 1 | review |
| 0 | 2 | travel |
| 0 | 3 | sale |