INSERT INTO public.role (id_role, role) VALUES (1, 'ADMIN');
INSERT INTO public.type_book (id_type, book_type) VALUES (1, 'education');
INSERT INTO public.user_account (id_user, first_name, last_name, email, password, active, creation_date, update_date) VALUES (1, 'Jeanne', 'Dupond', 'jd@gmail.com', '$2a$10$oHMzfrbOHT33uSfPxQVyNuF7WT09DyBHPK4yXDu5grb1DmUIcXVFC', 1, '2019-05-10', '2019-05-10');
INSERT INTO public.user_account (id_user, first_name, last_name, email, password, active, creation_date, update_date) VALUES (2, 'jean', 'Test', 'jt@gmail.com', '$2a$10$vrm5QdY63AXeXKnxz7vA4OtWDBcJe7zdDZOcnF5hh7XsfErm5bQjK', 1, '2019-05-10', '2019-05-10');
INSERT INTO public.user_account (id_user, first_name, last_name, email, password, active, creation_date, update_date) VALUES (3, 'Lucie', 'Test', 'lt@gmail.com', '$2a$10$bAbRMdOUMDwYUVsIfPcQrO7PxsWznOuWoZd3P4pEEAQd8HyxJ1EeK', 1, '2019-05-10', '2019-05-10');
INSERT INTO public.user_account (id_user, first_name, last_name, email, password, active, creation_date, update_date) VALUES (4, 'Laure', 'Louis', 'marj12@live.fr', '$2a$10$fVTLIJHi4QXBcnagb7UMj.EHUDmXzgjj..UsQlJA/alOnnJGVeZmS', 1, '2019-07-07', '2019-07-07');
INSERT INTO public.user_role (id_user, id_role) VALUES (1, 1);
INSERT INTO public.user_role (id_user, id_role) VALUES (2, 1);
INSERT INTO public.user_role (id_user, id_role) VALUES (3, 1);
INSERT INTO public.user_role (id_user, id_role) VALUES (4, 1);

INSERT INTO public.author (id_author, last_name, first_name, id_editeur) VALUES (1, 'Thierry', 'RICHARD', 1);
INSERT INTO public.author (id_author, last_name, first_name, id_editeur) VALUES (2, ' Daniel  - Sébastien  - William ', 'DJORDJEVIC - OLLIVIER -  KLEIN', 1);
INSERT INTO public.book (id_book, id_type, id_editeur, title, summary, url_image, isbn, purchase_date, price, creation_date, update_date) VALUES (1, 1, 1, 'Java EE', 'Ce livre s''adresse aux développeurs souhaitant monter en compétences sur le développement d''applications web, côté serveur, avec les technologies essentielles de la plateforme Java EE 7 (Java Enterprise Edition 7). Des connaissances sur le langage Java sont un prérequis nécessaire à la bonne compréhension du livre. 

Tout au long des chapitres, l''auteur aide le lecteur à mettre en place des projets au travers de l''IDE Eclipse lui permettant d''explorer le fonctionnement des technologies décrites.

Le premier chapitre présente Java EE 7, le protocole HTTP et l''environnement de développement utilisé (Java 8, Eclipse Mars, Tomcat 8 et MySQL 5.7). Les deux chapitres suivants présentent en détail les fondamentaux du développement web avec les servlets et les JSP ainsi que les technologies suivantes : les filtres, les événements, les sessions, les cookies, l''EL et les balises JSTL. Le quatrième chapitre traite de la persistance des données, un élément incontournable pour créer une application. La première partie de ce chapitre détaille l''utilisation de l''API JDBC et la seconde partie montre la puissance d''un ORM en présentant la spécification JPA. À l''issue de ces quatre premiers chapitres, le lecteur est capable de créer ses premières applications web en Java.

Pour aller encore plus loin dans la connaissance et la maîtrise des technologies Java liées au développement web, le cinquième chapitre présente la notion de Framework qui permet d''architecturer les applications et d''industrialiser le développement. À ce titre, les bases des Frameworks JSF et Struts 2 sont présentées. L''avant-dernier chapitre est dédié à la mise en œuvre de technologies complémentaires : les Services Web REST et les WebSockets. Enfin, le dernier chapitre s''attarde sur le déploiement d''une application sur Tomcat 8 en traitant des sujets incontournables que sont la sécurité (l''authentification, l''autorisation, HTTPS) et la journalisation.

Des éléments complémentaires sont en téléchargement sur le site www.editions-eni.fr.


Les chapitres du livre :
Avant-propos – Introduction à Java EE – Le traitement métier avec les servlets – La présentation avec les JSP – La persistance des données avec JDBC et JPA – Les frameworks JSF et Struts – Des technologies complémentaires – Déploiement d''applications sur Tomcat', 'java-ee-developpez-des-applications-web-en-java-nouvelle-edition-9782409006913_L.jpg', '978-2-409-00691-3', '2019-07-03', 54, '2019-07-08', '2019-07-08');
INSERT INTO public.book (id_book, id_type, id_editeur, title, summary, url_image, isbn, purchase_date, price, creation_date, update_date) VALUES (2, 1, 1, 'Java EE', 'Ce livre s''adresse aux développeurs souhaitant monter en compétences sur le développement d''applications web, côté serveur, avec les technologies essentielles de la plateforme Java EE 7 (Java Enterprise Edition 7). Des connaissances sur le langage Java sont un prérequis nécessaire à la bonne compréhension du livre. 

Tout au long des chapitres, l''auteur aide le lecteur à mettre en place des projets au travers de l''IDE Eclipse lui permettant d''explorer le fonctionnement des technologies décrites.

Le premier chapitre présente Java EE 7, le protocole HTTP et l''environnement de développement utilisé (Java 8, Eclipse Mars, Tomcat 8 et MySQL 5.7). Les deux chapitres suivants présentent en détail les fondamentaux du développement web avec les servlets et les JSP ainsi que les technologies suivantes : les filtres, les événements, les sessions, les cookies, l''EL et les balises JSTL. Le quatrième chapitre traite de la persistance des données, un élément incontournable pour créer une application. La première partie de ce chapitre détaille l''utilisation de l''API JDBC et la seconde partie montre la puissance d''un ORM en présentant la spécification JPA. À l''issue de ces quatre premiers chapitres, le lecteur est capable de créer ses premières applications web en Java.

Pour aller encore plus loin dans la connaissance et la maîtrise des technologies Java liées au développement web, le cinquième chapitre présente la notion de Framework qui permet d''architecturer les applications et d''industrialiser le développement. À ce titre, les bases des Frameworks JSF et Struts 2 sont présentées. L''avant-dernier chapitre est dédié à la mise en œuvre de technologies complémentaires : les Services Web REST et les WebSockets. Enfin, le dernier chapitre s''attarde sur le déploiement d''une application sur Tomcat 8 en traitant des sujets incontournables que sont la sécurité (l''authentification, l''autorisation, HTTPS) et la journalisation.

Des éléments complémentaires sont en téléchargement sur le site www.editions-eni.fr.


Les chapitres du livre :
Avant-propos – Introduction à Java EE – Le traitement métier avec les servlets – La présentation avec les JSP – La persistance des données avec JDBC et JPA – Les frameworks JSF et Struts – Des technologies complémentaires – Déploiement d''applications sur Tomcat', 'java-ee-developpez-des-applications-web-en-java-nouvelle-edition-9782409006913_L.jpg', '978-2-409-00691-3', '2019-07-08', 54, '2019-07-08', '2019-07-08');
INSERT INTO public.book (id_book, id_type, id_editeur, title, summary, url_image, isbn, purchase_date, price, creation_date, update_date) VALUES (3, 1, 1, 'Java EE', 'Ce livre s''adresse aux développeurs souhaitant monter en compétences sur le développement d''applications web, côté serveur, avec les technologies essentielles de la plateforme Java EE 7 (Java Enterprise Edition 7). Des connaissances sur le langage Java sont un prérequis nécessaire à la bonne compréhension du livre. 

Tout au long des chapitres, l''auteur aide le lecteur à mettre en place des projets au travers de l''IDE Eclipse lui permettant d''explorer le fonctionnement des technologies décrites.

Le premier chapitre présente Java EE 7, le protocole HTTP et l''environnement de développement utilisé (Java 8, Eclipse Mars, Tomcat 8 et MySQL 5.7). Les deux chapitres suivants présentent en détail les fondamentaux du développement web avec les servlets et les JSP ainsi que les technologies suivantes : les filtres, les événements, les sessions, les cookies, l''EL et les balises JSTL. Le quatrième chapitre traite de la persistance des données, un élément incontournable pour créer une application. La première partie de ce chapitre détaille l''utilisation de l''API JDBC et la seconde partie montre la puissance d''un ORM en présentant la spécification JPA. À l''issue de ces quatre premiers chapitres, le lecteur est capable de créer ses premières applications web en Java.

Pour aller encore plus loin dans la connaissance et la maîtrise des technologies Java liées au développement web, le cinquième chapitre présente la notion de Framework qui permet d''architecturer les applications et d''industrialiser le développement. À ce titre, les bases des Frameworks JSF et Struts 2 sont présentées. L''avant-dernier chapitre est dédié à la mise en œuvre de technologies complémentaires : les Services Web REST et les WebSockets. Enfin, le dernier chapitre s''attarde sur le déploiement d''une application sur Tomcat 8 en traitant des sujets incontournables que sont la sécurité (l''authentification, l''autorisation, HTTPS) et la journalisation.

Des éléments complémentaires sont en téléchargement sur le site www.editions-eni.fr.


Les chapitres du livre :
Avant-propos – Introduction à Java EE – Le traitement métier avec les servlets – La présentation avec les JSP – La persistance des données avec JDBC et JPA – Les frameworks JSF et Struts – Des technologies complémentaires – Déploiement d''applications sur Tomcat', 'java-ee-developpez-des-applications-web-en-java-nouvelle-edition-9782409006913_L.jpg', '978-2-409-00691-3', '2019-07-08', 54, '2019-07-08', '2019-07-08');
INSERT INTO public.book (id_book, id_type, id_editeur, title, summary, url_image, isbn, purchase_date, price, creation_date, update_date) VALUES (4, 1, 1, 'Angular', 'Ce livre permet aux lecteurs de se lancer dans le développement d''applications web avec le framework Angular (en version 4 au moment de l''écriture). Pour une meilleure compréhension de son contenu, il est nécessaire d''avoir un minimum de connaissances sur le fonctionnement du web et sur les langages HTML et JavaScript. Les auteurs ont eu à cœur de rédiger un livre très pragmatique avec de nombreux exemples de code, commentés et expliqués, qui illustrent de façon très concrète les passages plus théoriques.

Dans les premiers chapitres, pour pouvoir démarrer le développement d''une application avec Angular, les auteurs traitent des sujets à maîtriser que sont les composants, les templates, les pipes ou encore les modules. Le langage de Microsoft TypeScript et l''outil en ligne de commande CLI sont également étudiés.

Chacun des chapitres suivants détaille une brique précise du framework. Le lecteur y apprend ce qu''est un composant, un service ou une directive et comment les utiliser. Le fonctionnement de l''injection de dépendances ou du routage sont traités ainsi que la création d''un formulaire ou les interactions avec l''utilisateur.

Le livre se poursuit avec l''apprentissage des tests d''une application Angular, que ce soit au travers de tests unitaires ou d''intégration (end-to-end), ainsi que des différentes possibilités existantes pour rendre une application multiplateforme (ou cross-platform).

Enfin, un chapitre est consacré à des sujets plus avancés dans lequel les auteurs détaillent notamment la façon d''effectuer le rendu côté serveur ou encore le fonctionnement du moteur de détection de changements.


Les chapitres du livre :
Avant-propos – Introduction – Ma première application – Fondamentaux d''Angular – TypeScript – Angular CLI – Les composants – Les services – L''injection de dépendances – Le requêtage HTTP – Les interactions utilisateur – Les formulaires – Le routage – Les directives – Tester son application – Le cross-platform avec Angular – Pour aller plus loin', 'angular-developpez-vos-applications-web-avec-le-framework-javascript-de-google-9782409008979_L.jpg', '978-2-409-00897-9', '2019-07-08', 39, '2019-07-08', '2019-07-08');
INSERT INTO public.book (id_book, id_type, id_editeur, title, summary, url_image, isbn, purchase_date, price, creation_date, update_date) VALUES (5, 1, 1, 'Windows Server 2019',
 'Ce livre sur Windows Server 2019 est destiné aux administrateurs système ou aux techniciens en informatique qui souhaitent se former sur cette version du système d exploitation serveur de Microsoft ou mettre à jour leurs connaissances par rapport aux anciennes versions. Il est composé de parties théoriques toujours complétées de parties pratiques permettant de mettre en place les solutions étudiées.', 'windows-server-2019-les-bases-indispensables-pour-administrer-et-configurer-votre-serveur-9782409019678_L.jpg', '978-2-409-00897-9', '2019-07-08', 40, '2019-07-08', '2019-07-08');
INSERT INTO public.book (id_book, id_type, id_editeur, title, summary, url_image, isbn, purchase_date, price, creation_date, update_date) VALUES (6, 1, 1, 'HTML5, CSS3',
                                                                                                                                                  'Ce livre s''adresse à de grands débutants en développement informatique, qui n''ont jamais programmé avec HTML5, CSS3 et JavaScript. L''auteur guide le lecteur en lui enseignant des méthodes efficaces et actuelles pour créer son premier site web, en partant vraiment de zéro et en allant jusqu''à un niveau suffisant pour qu''il soit ensuite autonome.'
                                                                                                                                                   , 'HTML5-et-C3.jpg', '978-2-409-00897-9', '2019-07-08', 40, '2019-07-08', '2019-07-08');

INSERT INTO public.borrow (id_borrow, id_book, id_user, is_loan, borrowing_date, return_date, is_extended) VALUES (5, 1, 4, true, '2019-07-08', '2019-08-05', false);
INSERT INTO public.editor (id_editeur, name) VALUES (1, 'ENI');
