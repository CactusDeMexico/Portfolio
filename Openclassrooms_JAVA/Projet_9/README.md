## Testez vos développements Java / Test your Java developments

Projet 10 [ Développez le nouveau système d’information de la bibliothèque d’une grande ville_](https://openclassrooms.com/fr/projects/124/assignment)
sur [_OpenClassrooms_](https://www.openclassrooms.com).


## MPD
![](script/bdd_new.png?raw=true)


### Technologies

- JDK8 version 152
- Apache Tomcat 9.0.16
- Apache Maven 4.0
- PostgreSQL 9.6
- Jenkins , gitlab, JACOCO, log4j,Junit 


 

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up


## Les Correctifs

*   Dans l'entité `EcritureComptable`, correction de la méthode `getTotalCredit()` qui accédait à la méthode `getDebit()` au lieu de `getCredit()`
*   Dans l'entité `EcritureComptable`, correction de la méthode `isEquilibree()` qui retournait le résultat d'une égalité à l'aide de `equals()` au lieu de faire une comparaison avec `compareTo()`
*   Dans la classe `ResultSetHelper`, correction de la méthode `getDate()`. La variable `vDate` n'était pas redéfinie dans la condition
*   Dans le fichier `sqlContext.xml`, corriger la propriété `SQLinsertListLigneEcritureComptable`. Il manquait une virgule dans le INSERT entre les colonnes `debit` et `credit`
*   Dans la classe `ComptabiliteManagerImpl`, correction de la méthode `updateEcritureComptable()`. Ajouter la ligne `this.checkEcritureComptable(pEcritureComptable);` en haut afin de vérifier que la référence de l'écriture comptable respecte les règles de comptabilité 5 et 6
*   Dans la classe `SpringRegistry` de la couche business, modification de la variable `CONTEXT_APPLI_LOCATION` afin d'adapter le chemin d'accès au fichier `bootstrapContext.xml` qui est un conteneur Spring IoC, dans lequel on importe le `businessContext.xml`, `consumerContext.xml` et le `datasourceContext.xml` qui va redéfinir le bean `dataSourceMYERP` pour les tests
*   Correction de divers code smells indiqués par le plugin SonarLint de IntelliJ 


## Les Ajouts
*   Dans l'entité `SequenceEcritureComptable`, ajout de l'attribut `journalCode` avec son getter et son setter
*   Dans le consumer, ajout du RowMapper `SequenceEcritureComptableRM`
*   Dans l'interface `ComptabiliteDao`, ajout de la méthode `getSequenceByCodeAndAnneeCourante()`, implémentation de celle-ci dans `ComptabiliteDaoImpl` et définition de la requête correspondante `SQLgetSequenceByCodeAndAnneeCourante` dans le fichier `sqlContext.xml`
*   Dans l'interface `ComptabiliteDao`, ajout de la méthode `upsertSequenceEcritureComptable()`, implémentation de celle-ci dans `ComptabiliteDaoImpl` et définition de la requête correspondante `SQLupsertSequenceEcritureComptable` dans le fichier `sqlContext.xml`
*   Dans l'interface `ComptabiliteManager`, ajout de la méthode `upsertSequenceEcritureComptable()`, implémentation de celle-ci dans `ComptabiliteManagerImpl`
*   Dans la classe `ComptabiliteManagerImpl`, implémentation de la méthode `addReference()`
*   Dans la classe `ComptabiliteManagerImpl`, à la fin de la méthode `checkEcritureComptableUnit()`, vérifications permettant le respect de la règle de comptabilité 5
*   Configuration des tests d'intégration de la couche consumer dans le dossier `test-consumer`

## GitLab

Le fichier de configuration `.gitlab-ci.yml` de l'environnement d'intégration continue .
