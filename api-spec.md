# Book Car Interface Specification
## Interface Description
This interface provides rental customers with the ability to reserve a car primarily based on date.

## Basic Info

| Request Method | Request Path | Request Header                 |
|----------------|--------------|--------------------------------|
| POST           | /v1/bookings | content-type: application/json |

## Request
| Parameter Name | Type   | Required | Description               |
|----------------|--------|----------|---------------------------|
| carId          | int    | Y        | id of car                 |
| startDate      | string | Y        | start date of booking car |
| endDate        | string | Y        | end date of booking car   |


## Request Example
```shell
curl -X POST --location "http://localhost:8080/v1/bookings" \
    -H "content-type: application/json" \
    -d "{
          \"carId\": \"1\",
          \"startDate\": \"2022-03-16\",
          \"endDate\": \"2022-03-17\"
        }"
```
## Response 
| Name    | Type   | Description             |
|---------|--------|-------------------------|
| message | string | response status message |
| data    | object | response data           |
| error   | string | error info              |

## Response Example
```json
{
  "message": "success",
  "data": {
    "id": 10,
    "customerId": 1,
    "customerName": "admin",
    "carId": 4,
    "carModel": "BMW 650",
    "startDate": "2022-03-16",
    "endDate": "2022-03-17",
    "totalRent": 200,
    "status": 1
  }
}
```
## Error Response
```json
{
  "message": "Invalid parameter.",
  "error": "The car BMW 650 is already booked"
}   
```



