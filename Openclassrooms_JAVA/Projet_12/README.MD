## Aidez la communauté en tant que développeur d'application Java

Projet 12 [_Aidez la communauté en tant que développeur d'application Java](https://openclassrooms.com/fr/projects/139/assignment)
sur [_OpenClassrooms_](https://www.openclassrooms.com).


## MPD
![](script/new_bdd.png?raw=true)


### Technologies

- JDK8 version 152
- Apache Tomcat 9.0.16
- Apache Maven 4.0
- PostgreSQL 9.6
- API Rest
- JUnit
- Log4j

### Framework
- Spring MVC
- Spring Security 
- Thymeleaf

### Déploiement
#### Api REST

- Importation du projet dans IntelliJ
- Configuration de la base de données :

Acceder au fichier `library\library-webapp\src\main\resources\application.properties` et trouver les lignes suivantes :
```
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=postgres
spring.datasource.password=root
server.port=9090

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=exemple@gmail.com
spring.mail.password=password
```

Il suffit de paramètrer une base de données avec la même configuration, ou d'adapter cette dernière à une déjà existante.


#### Web-app
- Importation du projet dans IntelliJ
- Configuration de la base de données :

Acceder au fichier `library\library-webapp\src\main\resources\application.properties` et trouver les lignes suivantes :
```
spring.datasource.url=jdbc:postgresql://localhost:5432/db_architect
spring.datasource.username=postgres
spring.datasource.password=root
```

Il suffit de paramètrer une base de données avec la même configuration, ou d'adapter cette dernière à une déjà existante.

