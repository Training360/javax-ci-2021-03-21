# CI/CD oktatás

Build

```
gradlew build
gradlew -q dependencies 
gradlew dependencyUpdates
```

Spring Boot alkalmazás

```
gradlew bootRun
```

Elérhető:

http://localhost:8080/swagger-ui.html

```
gradlew test
```

Integrációs tesztek után:

```
gradlew test assemble
gradlew -i test assemble
gradlew integrationTest
```

MariaDB indítása:

```
docker run -d   
    -e MYSQL_DATABASE=employees    
    -e MYSQL_USER=employees    
    -e MYSQL_PASSWORD=employees    
    -e MYSQL_ALLOW_EMPTY_PASSWORD=yes   
    -p 3306:3306      
    --name employees-mariadb
    mariadb
```

Integrációs tesztek futtatása MariaDB-n:

```
gradlew clean -i 
    -Pspring.datasource.url=jdbc:mariadb://localhost/employees 
    -Pspring.datasource.username=employees 
    -Pspring.datasource.password=employees  integrationTest
```

Jenkins

```
docker network create jenkins

docker build -t employees-jenkins --file Dockerfile.jenkins .

docker run 
  --detach 
  --network jenkins 
  --volume jenkins-data:/var/jenkins_home 
  --volume /var/run/docker.sock:/var/run/docker.sock 
  --publish 8090:8080 
  --name employees-jenkins 
  employees-jenkins

docker exec -it employees-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

```
docker exec --user root -it employees-jenkins chmod 777 /var/run/docker.sock

git update-index --chmod=+x gradlew
```

```
docker run -d   
    -e MYSQL_DATABASE=employees    
    -e MYSQL_USER=employees    
    -e MYSQL_PASSWORD=employees    
    -e MYSQL_ALLOW_EMPTY_PASSWORD=yes     
    --name employees-it-mariadb
    --network jenkins
    -p 3307:3306
    mariadb
```

SonarQube

```
docker run --name employees-sonarqube --detach 
  --network jenkins 
  --publish 9000:9000 
  sonarqube:lts
```

```
gradlew sonarqube
```