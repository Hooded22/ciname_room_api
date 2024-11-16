This is the Cinema Room REST Service with Java project I made as part of [Java Backend Developer (Spring Boot)](https://hyperskill.org/courses/12-java-backend-developer-spring-boot)

Always wanted to have your private movie theater and screen only the movies you like? You can buy a fancy projector and set it up in a garage, but how can you sell tickets? The idea of a ticket booth is old-fashioned, so let's create a special service for that! Make good use of Spring and write a REST service that can show the available seats, sell and refund tickets, and display the statistics of your venue. Pass me the popcorn, please

Here's the link to the project: https://hyperskill.org/projects/197

Check out my profile: https://hyperskill.org/profile/180527031

## How to run locally
1. Add .env file

`.env` file sample data for local run

```
DB_URL=jdbc:mysql://localhost:3307/cinema_db
DB_USERNAME=root
DB_PASSWORD=cinema_db
REDIS_HOST=localhost
REDIS_PORT=6379
STATS_PASSWORD=some_password
```

2. Create MySQL database using sample docket command
```
docker run --name my-mysql-cinema -e MYSQL_ROOT_PASSWORD=cinema_db -e MYSQL_DATABASE=cinema_db -e MYSQL_PASSWORD=cinema_db -p 3307:3306 -d mysql:8.0
```

3. Run `Main.java` class