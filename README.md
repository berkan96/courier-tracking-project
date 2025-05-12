# courier-tracking-project

The main technologies I used in the courier tracking system project are listed below.

## Technologies Used

* Java 17
* Springboot 3
* Feign Client
* Apache Kafka
* Swagger
* PostgreSQL
* Mongodb
* Docker
* Mapstruct

## Architecture Diagram

![architecture](images/Architecture.jpg)

## Running on Your Computer

Navigate to the project directory in the terminal

```bash
  cd courier-tracking-project
```

* The docker-compose.yml file contains configurations for Kafka, PostgreSql, Mongodb, Zookeeper.

You can start these tools with the following command:

```docker
  docker-compose up -d
```

## Let's run the application step by step on Swagger and see the flow.

* First, let's run courier service, order service and store proximity service on your local.

### Create Courier Example

* __Courier service__ : <http://localhost:8082/swagger-ui/index.html#/>

```json
{
  "identityNo": "12342323423",
  "firstName": "courier1",
  "lastName": "test"
}
```

![create_courier](images/create-courier.png)

### Save Location Example

* __Courier service__ : <http://localhost:8082/swagger-ui/index.html#/>

```json
{
  "courierId": 1,
  "latitude": 42.6,
  "longitude": 46.5
}
```

![save-location](images/send-location.png)

### Update Status Example

* __Courier service__ : <http://localhost:8082/swagger-ui/index.html#/>


![update-status](images/update-status.png)


### Get Total Distance

```json
{
  "courierId": 101,
  "startDate": "2025-05-10",
  "endDate": "2025-05-12"
}
```

![get-total-distance](images/get-total-distancee.png)
