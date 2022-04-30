-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 01 mars 2019 à 09:23
-- Version du serveur :  5.7.23
-- Version de PHP :  7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `ocpizza`
--

--
-- Déchargement des données de la table `bill`
--

INSERT INTO `bill` (`IdBill`, `IdOrder`, `Amount`, `PaidStatus`) VALUES
(1, 1, '16', 'payé');

--
-- Déchargement des données de la table `cart`
--

INSERT INTO `cart` (`IdCart`, `IdUser`, `Quantity`) VALUES
(1, 1, 2);

--
-- Déchargement des données de la table `cart_product`
--

INSERT INTO `cart_product` (`IdCart`, `IdUser`, `IdProduct`) VALUES
(0, 1, 0),
(1, 2, 1),
(1, 2, 1),
(1, 2, 1);

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`IdCategory`, `Name`, `Description`) VALUES
(1, 'Creamy', 'Base Creme Fraiche'),
(2, 'BQQ', 'Base Barbercue'),
(3, 'Classique', 'Base tomate');

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`IdUser`, `Adress`) VALUES
(2, '26 rue ici');

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`IdUser`, `IdRestaurant`, `AccountType`) VALUES
(1, 1, 'manager'),
(3, 1, 'seller'),
(4, 1, 'preparator'),
(5, 1, 'delivery');

--
-- Déchargement des données de la table `ingredient`
--

INSERT INTO `ingredient` (`IdIngredient`, `Name`) VALUES
(1, 'Olive'),
(2, 'Oignon'),
(3, 'Champignon'),
(4, 'Jambon'),
(5, 'Fromage'),
(6, 'Creme fraiche'),
(7, 'Sauce tomate');

--
-- Déchargement des données de la table `order_table`
--

INSERT INTO `order_table` (`IdOrder`, `IdCart`, `IdUser`, `IdRestaurant`, `OrderType`, `Status`, `OnlinePayment`, `Amount`, `DeliveryAddress`, `DeliveryTime`) VALUES
(1, 1, 2, 1, 'no delivery', 'in progress', 1, 16, 'none', '18h45');

--
-- Déchargement des données de la table `receipeingredient`
--

INSERT INTO `receipeingredient` (`IdRecipe`, `Quantity`, `IdIngredient`) VALUES
(1, 20, 6),
(1, 20, 3),
(1, 20, 5),
(1, 20, 2),
(2, 20, 4),
(2, 20, 3),
(2, 20, 5);

--
-- Déchargement des données de la table `recipe`
--

INSERT INTO `recipe` (`IdRecipe`, `Name`) VALUES
(1, 'Reine'),
(2, 'Classique Jambon');

--
-- Déchargement des données de la table `restaurant`
--

INSERT INTO `restaurant` (`IdRestaurant`, `Name`, `Address`, `Schedule`) VALUES
(1, 'Pizzeria Evry', '20 rue ici', '10h-18h'),
(2, 'Pizzeria Grigny', '30 rue ici', '10h-18h'),
(3, 'Pizzeria Ris Orangis', '40 rue ici', '10h-18h');

--
-- Déchargement des données de la table `stock`
--

INSERT INTO `stock` (`IdStock`, `Quantity`, `IdRestaurant`, `IdIngredient`) VALUES
(1, 200, 1, 1),
(2, 200, 1, 2),
(3, 200, 1, 3),
(4, 200, 1, 4),
(5, 200, 1, 5),
(6, 200, 1, 6),
(7, 200, 1, 7),
(8, 200, 2, 1),
(9, 200, 2, 2),
(10, 200, 2, 3),
(11, 200, 2, 4),
(12, 200, 2, 5),
(13, 200, 2, 6),
(14, 200, 2, 7),
(15, 200, 3, 1),
(16, 200, 3, 2),
(17, 200, 3, 3),
(18, 200, 3, 4),
(19, 200, 3, 5),
(20, 200, 3, 6),
(21, 200, 3, 7);

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`IdUser`, `Name`, `LastName`, `LoginName`, `Password`) VALUES
(1, 'Jean', 'LaCroix', 'Jcroix@gmail.com', 'hg658478'),
(2, 'Lucy', 'Durant', 'LDurant@gmail.com', 'hg6fefs8'),
(3, 'Louis', 'Dupont', 'LDupont@gmail.com', 'gezeg78'),
(4, 'Guy', 'Luce', 'GLuce@gmail.com', 'gegju_478'),
(5, 'Marc', 'Lupon', 'MLupon@gmail.com', 'gegju_478');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
