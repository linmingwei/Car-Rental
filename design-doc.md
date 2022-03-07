# Car Rental Design Document

## Booking Car Interface

### Design
In the implementation of the interface for booking a car, three resources need to be involved: customers, vehicles, and orders, so three tables are needed in the database to store these three resources. The three tables are customer, car, booking.  

**Table Structure:**
```
customer:
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| id       | bigint       | NO   | PRI | NULL    | auto_increment |
| username | varchar(50)  | NO   | MUL |         |                |
| password | varchar(255) | NO   |     | NULL    |                |
| mobile   | char(11)     | NO   |     | NULL    |                |
| email    | varchar(200) | NO   |     |         |                |
| status   | tinyint      | NO   |     | 0       |                |
+----------+--------------+------+-----+---------+----------------+

car:
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | bigint       | NO   | PRI | NULL    | auto_increment |
| car_model  | varchar(255) | NO   | MUL |         |                |
| rent_price | int          | NO   | MUL | 0       |                |
| status     | tinyint      | NO   |     | 0       |                |
+------------+--------------+------+-----+---------+----------------+

booking:
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| id            | bigint       | NO   | PRI | NULL    | auto_increment |
| customer_id   | bigint       | NO   | MUL | NULL    |                |
| customer_name | varchar(50)  | NO   |     |         |                |
| car_id        | bigint       | NO   |     | NULL    |                |
| car_model     | varchar(255) | NO   |     |         |                |
| start_date    | date         | NO   | MUL | NULL    |                |
| end_date      | date         | NO   |     | NULL    |                |
| total_rent    | int          | NO   |     | 0       |                |
| status        | tinyint      | NO   |     | 0       |                |
+---------------+--------------+------+-----+---------+----------------+
```
### Flow
To book a car, the customer first needs to log in, then select the car that needs to be booked, then select the time to book, and finally confirm the booking. The interface requires the customer's personal information, so the customer first needs to log in to the rental car system. In addition to the customer's personal information, the interface also needs to provide vehicle information, so it is necessary to query the vehicle information to check whether the reserved car is available and whether it conflicts with other scheduled dates. In addition, the scheduled date also needs to be checked whether it is logical, that is, the scheduled date must be greater than the current date and so on. When multiple customers book the same date for the same car at the same time, it is necessary to ensure that only one user can book successfully.
### Assumptions & Solution
* Repeated submit of the same booking request  
  Solution: For each request, store token+method+params as a unique key, and judge whether it exists when requesting again. If it already exists, it is considered a duplicate submit.
* A large number of malicious calls in short time  
  Solution: Use the token bucket algorithm as a current limiter to limit the number of calls per second of the interface.
* A large number of interface calls, the interface call time is too long  
  Solution: Change the synchronized lock of the book Car method to a shard lock divided according to the car Id to increase the degree of concurrency.