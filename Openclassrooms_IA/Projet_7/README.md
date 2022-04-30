# Détectez les Bad Buzz grâce au Deep Learning


Projet 7 [_Détectez les Bad Buzz grâce au Deep Learning_](https://www.kaggle.com/kazanova/sentiment140)
sur [_OpenClassrooms_](https://www.openclassrooms.com).

## 📗 Context
Votre entreprise souhaite améliorer sa plateforme avec une nouvelle fonctionnalité de collaboration.
Les utilisateurs pourront par exemple poster des avis et des photos sur leur restaurant préféré. 
Ce sera aussi l’occasion, pour l’entreprise, de mieux comprendre les avis postés par les utilisateurs.


## 🎯 Objectif
Analyse exploratoire
Apprentissage non supervisé
Deep Learning sur des données textuelles
## 💻 Technologies
- Pandas
- Numpy
- Matplotlib
- Seaborn
- Scipy
- Sklearn (GridSearchCV, LogisticRegression, t-SNE ,LatentDirichletAllocation)
- Tensorflow
- TF-IDF :term frequency-inverse document frequency
- Dash

## 📄 Dataset
[_yelp_](https://www.yelp.com/dataset)
## 📈 Compétences évaluées

###  🎓  Compétences : <i>Collecter des données venant d’une API qui correspondent à un besoin défini
#### La collecte des données via une API est complète si :
>- une requête pour obtenir les données via l’API a été  écrite, testée et renvoie bien des données en retour
#### La collecte des données via une API est pertinente si :
>- seuls les champs nécessaires ont été récupérés 
>- au moins un filtre a été appliqué sur un des champs nécessaires pour ne collecter que les avis ayant les valeurs correspondantes sur ces champs 
#### La collecte des données via une API est pertinente si :
>- les données collectées via l’API sont stockées dans un fichier utilisable (ex. : fichier CSV, avec en colonnes les différentes informations correspondant aux avis)

###  🎓  Compétences : <i>Effectuer un prétraitement de données non structurées pour obtenir un jeu de données utilisable
#### Le prétraitement de données non structurées pour obtenir un jeu de données utilisable est complet si :
>- les champs de texte sont nettoyés : la ponctuation et les mots de liaison ont été retirés, les chaînes de caractères ont été mises en minuscules
>- au moins une parmi les trois transformations “stemming”, “tokenization”, “lemmatization” a été appliquée 
>- le bruit sur les images a été filtré
>- l’histogramme a été égalisé sur les images

#### Le prétraitement de données non structurées pour obtenir un jeu de données utilisable est pertinent si :
>- pour le texte, au moins un bag-of-words a été créé, incluant des étapes de nettoyage supplémentaires, comme un seuil de fréquence et la normalisation des mots
>- pour les images, un algorithme d’extraction de features a été créé (ORB, SIFT, SURF), et un algorithme de Transfert Learning basé sur des réseaux de neurones, comme par exemple CNN, a été créé

#### Le prétraitement de données non structurées pour obtenir un jeu de données utilisable est présentable si :
>- l’enchaînement des étapes de nettoyage et de création de variables pour les images et le texte a été automatisé (en écrivant les différentes étapes sous forme de fonction, puis en utilisant un pipeline) et a été testé sur un exemple

###  🎓  Compétences : <i> Utiliser des techniques de réduction de la dimension
	
#### L’utilisation des techniques de réduction de la dimension est complète si :

>- une méthode de réduction de dimension a été appliquée sur les données texte et sur les données images

#### L’utilisation des techniques de réduction de la dimension est pertinente si :

>- une justification du fait d’appliquer une réduction de dimension sur les données texte et image a été donnée

>- une justification des choix des valeurs des paramètres dans la méthode de réduction de dimension retenue a été donnée (ex. : le nombre de dimensions conservées pour l'ACP)

###  🎓 Compétences : <i>Visualiser des données de grandes dimensions
#### La visualisation des données en grande dimension est complète si :

>- au moins un graphique représentant des informations contenant plus de deux dimensions a été réalisé 


#### La visualisation des données en grande dimension est pertinente si :

>-  le choix de la méthode de représentation graphique a été justifié (T-SNE…)

>- la lecture du graphique a été facilitée en explicitant les différents éléments pour un public non expert

 

#### La visualisation des données en grande dimension est présentable si :

>-  le graphique réalisé est lisible et compréhensible (ex. : taille des titres et légende)

