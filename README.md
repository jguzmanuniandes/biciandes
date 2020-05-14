# LPS BiciAndes

## How to run

Look for root folder and then compile. Be sure you have 
**application.properties** file setted with database and aws credentials. Then:

```
mvn clean package -Dspring.profiles.active=free
```

Run: You can choose free, beginner, master

```
java -jar -Dspring.profiles.active=free target/biciandes-0.0.1.jar
```

Results

```
localhost:8080
```
