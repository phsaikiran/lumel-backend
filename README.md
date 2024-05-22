# lumel-backend

# Schema diagram
- Customer
- Product
- Order

![Schema](Schema.png)

# APIs

| Method | URL             | Request            | Response                    |
|--------|-----------------|--------------------|-----------------------------|
| GET    | /loadCSV        | None               | SUCCESS \| PARTIAL \| ERROR |
| GET    | /totalCustomers | startDate, endDate | {"error": "","count": 2}    |
| GET    | /totalOrders    | startDate, endDate | {"error": "","count": 3}    |


# Example
GET /load-csv

[Example: http://localhost:8080/loadCSV](http://localhost:8080/loadCSV)


# Instructions to run
- Need Java 17

```./mvnw spring-boot:run```