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

Quality Gate

https://tomgregory.com/sonarqube-quality-gates-in-jenkins-build-pipeline/

Buildnumber

```
gradlew.bat -PbuildNumber=34 bootJar
```

Nexus

```
docker run --name employees-nexus --detach
  --network jenkins
  --publish 8091:8081
  --volume nexus-data:/nexus-data
  sonatype/nexus3

docker exec -it employees-nexus cat /nexus-data/admin.password
```

```
gradlew -PbuildNumber=3 publish
```

Alkalmazás Dockerben

```
docker network create my-employees

docker run -d  -e MYSQL_DATABASE=employees -e MYSQL_USER=employees -e MYSQL_PASSWORD=employees -e MYSQL_ALLOW_EMPTY_PASSWORD=yes --name employees-my-mariadb --network my-employees -p 3308:3306 mariadb

docker run  -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://employees-my-mariadb/employees -e SPRING_DATASOURCE_USERNAME=employees -e SPRING_DATASOURCE_PASSWORD=employees --network my-employees --name my-employees-hub training360/employees-ci

# Dockerfile létrehozása

docker build -t employees .

docker run  -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://employees-my-mariadb/employees -e SPRING_DATASOURCE_USERNAME=employees -e SPRING_DATASOURCE_PASSWORD=employees --network my-employees --name my-employees employees
```

```
gradlew    
    -Pspring.datasource.url=jdbc:mariadb://employees-my-mariadb/employees    
    -Pspring.datasource.username=employees     
    -Pspring.datasource.password=employees    
    dockerRun
```