## Créez un site communautaire autour de l’escalade

Projet 6 [_Créez un site communautaire autour de l’escalade_](https://openclassrooms.com/projects/creez-un-site-communautaire-autour-de-lescalade)
sur [_OpenClassrooms_](https://www.openclassrooms.com).


## MPD
![](script/mpd.PNG?raw=true)


### Technologies

- JDK8 version 152
- Apache Tomcat 9.0.16
- Apache Maven 4.0
- PostgreSQL 9.6

### Framework
- Spring MVC
- Spring Security 
- Thymeleaf

### Déploiement

- Importation du projet dans IntelliJ
- Configuration de la base de données :

Acceder au fichier `climb/src/main/java/resources/application.properties` et trouver les lignes suivantes :
```
spring.datasource.url=jdbc:postgresql://localhost:5432/climb?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC
spring.datasource.username=postgres
spring.datasource.password=root
```

Il suffit de paramètrer une base de données avec la même configuration, ou d'adapter cette dernière à une déjà existante.

