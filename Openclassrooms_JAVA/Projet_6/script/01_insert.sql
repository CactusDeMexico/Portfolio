
INSERT INTO public.user1 (iduser, name, lastname, email, password, active) VALUES (1, 'Jeanne', 'Dupond', 'jd@gmail.com', '$2a$10$oHMzfrbOHT33uSfPxQVyNuF7WT09DyBHPK4yXDu5grb1DmUIcXVFC', 1);
INSERT INTO public.user1 (iduser, name, lastname, email, password, active) VALUES (2, 'jean', 'Test', 'jt@gmail.com', '$2a$10$vrm5QdY63AXeXKnxz7vA4OtWDBcJe7zdDZOcnF5hh7XsfErm5bQjK', 1);
INSERT INTO public.user1 (iduser, name, lastname, email, password, active) VALUES (3, 'Lucie', 'Test', 'lt@gmail.com', '$2a$10$bAbRMdOUMDwYUVsIfPcQrO7PxsWznOuWoZd3P4pEEAQd8HyxJ1EeK', 1);


INSERT INTO public.user_role (iduser, idrole) VALUES (1, 1);
INSERT INTO public.user_role (iduser, idrole) VALUES (2, 1);
INSERT INTO public.user_role (iduser, idrole) VALUES (3, 1);


INSERT INTO public.publication (idpublication, name, iduser, creationdate, updatedate) VALUES (24, 'Le volcan', 1, '2019-05-10', '2019-05-10');
INSERT INTO public.publication (idpublication, name, iduser, creationdate, updatedate) VALUES (40, 'Les Pytons', 1, '2019-05-15', '2019-05-15');
INSERT INTO public.publication (idpublication, name, iduser, creationdate, updatedate) VALUES (50, 'La pelée', 1, '2019-05-26', '2019-05-26');
INSERT INTO public.publication (idpublication, name, iduser, creationdate, updatedate) VALUES (58, 'Desert', 1, '2019-05-27', '2019-05-27');
INSERT INTO public.publication (idpublication, name, iduser, creationdate, updatedate) VALUES (76, 'Cascade', 2, '2019-05-28', '2019-05-28');


INSERT INTO public.topo (idtopo, lieu, hiddentopo) VALUES (51, 'Martinique', false);
INSERT INTO public.topo (idtopo, lieu, hiddentopo) VALUES (41, 'SaintLucie', false);
INSERT INTO public.topo (idtopo, lieu, hiddentopo) VALUES (25, 'Guadeloupe', false);
INSERT INTO public.topo (idtopo, lieu, hiddentopo) VALUES (59, 'USA', false);
INSERT INTO public.topo (idtopo, lieu, hiddentopo) VALUES (77, 'Croatie', false);

INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (26, 25, 24, 'Soufrière', 'La Soufrière, surnommée « vié madanm la » en créole guadeloupéen, littéralement « la vieille dame » en français, est un volcan en activité situé sur le territoire de la commune de Saint-Claude en Guadeloupe, dans le parc national du même nom, dans le Sud de l''île de Basse-Terre', 'soufriere.jpg');
INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (78, 77, 76, 'Badami', 'Depuis quelques années, Badamii est devenu une destination prisée des grimpeurs. On peut en effet jouir des joies de la grimpe sur du grés rouge, tout en savourant le calme, la tranquillité et le dépaysement qu’offrent les lieux.', 'soufriere.jpg');
INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (1, 25, 24, 'Antalya', 'Geyikbayiri ou Akyalar, tels sont les noms des falaises du coin. C’est pourquoi le site est plus connu sous le nom d’Antalya. Petite perle proche de la mer, ces falaises baignées de soleil, ceci combiné avec la culture turque et leur amabilité, en font des falaises incroyables et uniques. ', 'photo1.jpg');
INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (42, 41, 40, 'Bouillante', 'Bouillante est une commune française, située dans le DOM-ROM archipel de Guadeloupe. La commune est notable en raison de la réserve Cousteau à Malendure et de la centrale géothermique de Bouillante qui est l''une des deux centrales géothermiques de Fran', 'stlucie.png');
INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (52, 51, 50, 'La pelée', 'La montagne Pelée est un volcan actif situé dans le Nord de la Martinique, une île des petites Antilles constituant un département d''outre-mer de France', 'les-chutes-du-carbet.jpg');
INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (60, 59, 58, 'Canyon', 'Le Grand Canyon ou Grand Cañon, en anglais Grand Canyon, en hopi Ongtupqa, en yavapai Wi:kaʼi:la, est un canyon des États-Unis situé dans le Nord-Ouest de l''Arizona. ', 'les-chutes-du-carbet.jpg');
INSERT INTO public.spot (idspot, idtopo, idpublication, nom, description, lien) VALUES (70, 25, 24, 'Carbet', 'Un carbet est un abri de bois sans mur typique des cultures amérindiennes. On en trouve notamment en Guyane, au Brésil, au Suriname et dans certaines îles antillaises.', 'Cap-Salomon-5-min-770x515.jpg');



INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (43, 42, 40, 'vieux fort', 'sud', 'sportive', 'gros_piton.jpg', 90);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (1, 1, 24, 'petit plateau', 'ouest', 'traditionnel', 'photo2.jpg', 150);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (27, 26, 24, 'Saint claude', 'nord', 'sportive', 'stlucie.png', 54);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (53, 52, 50, 'aileron ', 'sud', 'sportive', 'téléchargement.jpg', 240);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (79, 78, 76, 'petit badami', 'est', 'sportive', 'zd.jpg', 14);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (65, 26, 24, 'petit bourg', 'nord', 'sportive', 'zd.jpg', 4);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (71, 70, 24, 'les chutes', 'ouest', 'sportive', 'ruille.jpg', 63);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (68, 60, 24, 'grand chef', 'sud', 'sportive', 'stlucie.png', 3);
INSERT INTO public.secteur (idsecteur, idspot, idpublication, nom, lieu, type, lien, hauteur) VALUES (61, 60, 58, 'hibou', 'sud', 'sportive', 'ruille.jpg', 30);



INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (3, 27, 'petit chemin', true, 'ours', '5c');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (57, 27, 'petite lune', true, 'lune', '8c');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (62, 61, 'petit rouge', false, 'rouge', '3a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (72, 71, 'pied de damme', false, 'tete', '3a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (28, 27, 'petit champs ', false, 'grand champ', '3a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (4, 1, 'le petit crabe', false, 'crabe', '5c');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (80, 79, 'aigle', true, 'nid', '8a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (44, 43, 'petite etoile', false, 'etoile', '8c');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (66, 65, 'cacaoyer', false, 'cacao', '3a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (5, 1, 'le grand crabe', true, 'marmite', '8a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (54, 53, 'mahogani', false, 'branche', '9a');
INSERT INTO public.voie (idvoie, idsecteur, nom, equipees, relai, cotation) VALUES (69, 68, 'icaquier', false, 'icaque', '3a');



INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (46, 1, 'de-yujde-yu', 40, false);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (47, 1, 'le petit test', 40, false);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (55, 1, 'Très beau', 24, true);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (56, 1, 'beau', 24, true);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (73, 1, 'bien', 24, true);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (74, 1, 'xxx', 24, false);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (75, 1, 'dddd', 24, false);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (88, 2, 'xxx', 40, false);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (89, 2, 'dggdg', 40, false);
INSERT INTO public.commentaire (idcommentaire, iduser, texte, idpublication, hiddencom) VALUES (90, 3, 'xcwc', 24, false);


INSERT INTO public.proprietaire (idtopo, iduser) VALUES (25, 1);
INSERT INTO public.proprietaire (idtopo, iduser) VALUES (41, 1);
INSERT INTO public.proprietaire (idtopo, iduser) VALUES (51, 1);
INSERT INTO public.proprietaire (idtopo, iduser) VALUES (59, 1);
INSERT INTO public.proprietaire (idtopo, iduser) VALUES (77, 2);



INSERT INTO public.rent (idtopo, isloan, borrowingdate, return, iduser, isborrow, isseen) VALUES (51, false, '2019-05-26', '2019-05-26', 1, false, true);
INSERT INTO public.rent (idtopo, isloan, borrowingdate, return, iduser, isborrow, isseen) VALUES (41, false, '2019-07-20', '2019-08-22', 1, false, true);
INSERT INTO public.rent (idtopo, isloan, borrowingdate, return, iduser, isborrow, isseen) VALUES (59, false, '2019-05-27', '2019-05-27', 1, false, true);
INSERT INTO public.rent (idtopo, isloan, borrowingdate, return, iduser, isborrow, isseen) VALUES (25, false, '2019-05-10', '2019-06-20', 2, false, false);







