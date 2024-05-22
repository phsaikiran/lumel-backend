# lumel-backend

# Schema diagram
- Customer
- Product
- Order

![Schema](Schema.png)

# APIs

| Method | URL                | Request            | Response                    |
|--------|--------------------|--------------------|-----------------------------|
| GET    | /loadCSV           | None               | SUCCESS \| PARTIAL \| ERROR |
| GET    | /totalCustomers    | startDate, endDate | {"error": "","count": 2}    |
| GET    | /totalOrders       | startDate, endDate | {"error": "","count": 3}    |
| GET    | /averageOrderValue | startDate, endDate | {"error": "","count": 3}    |


# Examples

[http://localhost:8080/loadCSV](http://localhost:8080/loadCSV)

[http://localhost:8080/totalCustomers?startDate=2024-04-22&endDate=2024-12-12](http://localhost:8080/totalCustomers?startDate=2024-04-22&endDate=2024-12-12)

[http://localhost:8080/averageOrderValue?startDate=2020-04-22&endDate=2024-12-12](http://localhost:8080/averageOrderValue?startDate=2020-04-22&endDate=2024-12-12)


# Instructions to run
- Need Java 17

```./mvnw spring-boot:run```