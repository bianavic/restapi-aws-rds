# REST API WITH HATEOAS AND RDS AWS

### tools:
- linux
- intellij
- java 17
- spring data
- spring web
- spring hateoas
- rds aws
- postgresql

## Customer

| API ROUTE		                       | DESCRIPTION                  | STATUS |
|:----------------------------------|:-----------------------------|:-------|
| [POST] /customers/add             | Add a new customer           | 201    |
| [GET] /customers/customer/{id}    | Retrieve a customer by ID    | 200    |
| [GET] /customers	                 | Retrieve all the customers   | 200    |
| [PUT] /customers	                 | Update a customer by ID      | 200    |
| [DELETE] /customers/customer/{id} | Delete a customer by ID          | 204    |


#### Pre-Loaded data into Customer Database

| id | address | name | occupation |
| :--- | :--- | :--- | :--- |
| 1 | avenida silveira dutra 1002 | Maria Silva | Engineer |
| 2 | rua joao freire 231 | John Dutra | Teacher |
| 3 | The shine | Bilbo Baggins | Chef |


#### add customer
```bash
curl --location 'http://localhost:8001/customers/add' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Joao Carlos",
    "occupation": "Thief",
    "address": "Rua Test 51"
}'
```
###### 201 CREATED
``` json
{
    "id": 4,
    "name": "Joao Carlos",
    "occupation": "Thief",
    "address": "Rua Test 51"
}
```

#### get all customers
```bash
curl --location 'http://localhost:8001/customers/all'
```
###### 200 OK
``` json
[
    {
        "id": 1,
        "name": "Maria Silva",
        "occupation": "Engineer",
        "address": "avenida silveira dutra 1002",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/customers/customer/1"
            }
        ]
    },
    {
        "id": 2,
        "name": "John Dutra",
        "occupation": "Teacher",
        "address": "rua joao freire 231",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/customers/customer/2"
            }
        ]
    },
    {
        "id": 3,
        "name": "Bilbo Baggins",
        "occupation": "Chef",
        "address": "The shine",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/customers/customer/3"
            }
        ]
    },
    {
        "id": 4,
        "name": "Alice Morgan",
        "occupation": "Teacher",
        "address": "Street Test 234",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8001/customers/customer/4"
            }
        ]
    }
]
```

#### get a customer by ID
```bash
curl --location 'http://localhost:8001/customers/customer/2'
```
###### 200 OK
``` json
{
    "id": 2,
    "name": "John Dutra",
    "occupation": "Teacher",
    "address": "rua joao freire 231",
    "_links": {
        "Customers list": {
            "href": "http://localhost:8001/customers/all"
        },
        "self": {
            "href": "http://localhost:8001/customers/customer/2"
        }
    }
}
```

#### update a customer by ID
```bash
curl --location --request PUT 'http://localhost:8001/customers/update/4' \
--header 'Content-Type: application/json' \
--data '{
    "id": 4,
    "name": "Alice Morgan",
    "occupation": "Tcher",
    "address": "Street Test 234"
}'
```
###### 200 OK
``` json
{
    "id": 4,
    "name": "Alice Morgan",
    "occupation": "Teacher",
    "address": "Street Test 234"
}
```

#### delete a customer by ID
```bash
curl --location --request DELETE 'http://localhost:8001/customers/customer/5'
```
###### 204 NO CONTENT
``` json

```

## Order

| API ROUTE		                  | DESCRIPTION                         | STATUS |
|:-----------------------------|:------------------------------------|:-------|
| [POST] /orders/add           | Add a new order                     | 201    |
| [GET] /orders/order/{id}     | Retrieve an order by ID             | 200    |
| [GET] /orders	               | Retrieve all the orders             | 200    |
| [PUT] /orders	               | Update an order by ID               | 200    |
| [PUT] /orders/{id}/complete	 | Update an order status to completed | 200    |
| [PUT] /orders/{id}/cancel	   | Update an order status to cancelled | 200    |
| [DELETE] /orders/order/{id}  | Delete an order by ID               | 204    |

#### Pre-Loaded data into Order Database

| status | id | description |
| :--- | :--- | :--- |
| 1 | 1 | review |
| 2 | 2 | travel |
| 0 | 3 | sale |

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

#### [complete] update status order by ID
```bash
curl --location --request PUT 'http://localhost:8001/orders/4/complete' \
--header 'Content-Type: application/json' \
--data '{
    "status": "COMPLETED",
    "description": "review"
}'
```
###### 200 OK
``` json
{
    "id": 4,
    "status": "COMPLETED",
    "description": "review",
    "_links": {
        "self": {
            "href": "http://localhost:8001/orders/order/4"
        },
        "Order list": {
            "href": "http://localhost:8001/orders/all"
        }
    }
}
```

#### [cancel] update status order by ID
```bash
curl --location --request PUT 'http://localhost:8001/orders/5/cancel' \
--header 'Content-Type: application/json' \
--data '{
    "status": "COMPLETED",
    "description": "review"
}'
```
###### 200 OK
``` json
{
    "id": 5,
    "status": "CANCELLED",
    "description": "shop",
    "_links": {
        "self": {
            "href": "http://localhost:8001/orders/order/5"
        },
        "Order list": {
            "href": "http://localhost:8001/orders/all"
        }
    }
}
```

#### delete an order by ID
```bash
curl --location --request DELETE 'http://localhost:8001/orders/order/4'
```
###### 204 NO CONTENT
``` json

```

## Exceptions

| EXCEPTION NAME	                  | DESCRIPTION	                                            | HTTP STATUS CODE |
|:---------------------------------|:----------------------------------------------------------|:-----------------|
| CustomerNotFoundExceptionHateoas | Could not find id                                         | 404              |
| OrderNotFoundExceptionHateoas    | Could not find id                                         | 404              |
| Method Not Allowed		    | You can't complete the task, the order has a COMPLETED status |  405             |
