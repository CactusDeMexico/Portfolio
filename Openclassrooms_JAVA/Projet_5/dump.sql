-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 14 mars 2019 à 07:34
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

-- --------------------------------------------------------

--
-- Structure de la table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `IdBill` int(11) NOT NULL AUTO_INCREMENT,
  `IdOrder` int(11) NOT NULL,
  `Amount` decimal(10,0) NOT NULL,
  `PaidStatus` enum('non payé','payé') NOT NULL,
  PRIMARY KEY (`IdBill`,`IdOrder`),
  KEY `order_bill_fk` (`IdOrder`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bill`
--

INSERT INTO `bill` (`IdBill`, `IdOrder`, `Amount`, `PaidStatus`) VALUES
(1, 1, '16', 'payé');

-- --------------------------------------------------------

--
-- Structure de la table `cart`
--

DROP TABLE IF EXISTS `cart`;
CREATE TABLE IF NOT EXISTS `cart` (
  `IdCart` int(11) NOT NULL AUTO_INCREMENT,
  `IdUser` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  PRIMARY KEY (`IdCart`,`IdUser`),
  KEY `user_cart_fk` (`IdUser`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `cart`
--

INSERT INTO `cart` (`IdCart`, `IdUser`, `Quantity`) VALUES
(1, 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `cart_product`
--

DROP TABLE IF EXISTS `cart_product`;
CREATE TABLE IF NOT EXISTS `cart_product` (
  `IdCart` int(11) NOT NULL,
  `IdUser` int(11) NOT NULL,
  `IdProduct` int(11) NOT NULL,
  KEY `product_cart_product_fk` (`IdProduct`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `cart_product`
--

INSERT INTO `cart_product` (`IdCart`, `IdUser`, `IdProduct`) VALUES
(0, 1, 0),
(1, 2, 1),
(1, 2, 1),
(1, 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `IdCategory` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Description` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdCategory`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`IdCategory`, `Name`, `Description`) VALUES
(1, 'Creamy', 'Base Creme Fraiche'),
(2, 'BQQ', 'Base Barbercue'),
(3, 'Classique', 'Base tomate');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `IdUser` int(11) NOT NULL,
  `Adress` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdUser`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`IdUser`, `Adress`) VALUES
(2, '26 rue ici');

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

DROP TABLE IF EXISTS `employe`;
CREATE TABLE IF NOT EXISTS `employe` (
  `IdUser` int(11) NOT NULL,
  `IdRestaurant` int(11) NOT NULL,
  `AccountType` enum('seller','preparator','manager','delivery') NOT NULL,
  PRIMARY KEY (`IdUser`,`IdRestaurant`),
  KEY `restaurant_employe_fk` (`IdRestaurant`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`IdUser`, `IdRestaurant`, `AccountType`) VALUES
(1, 1, 'manager'),
(3, 1, 'seller'),
(4, 1, 'preparator'),
(5, 1, 'delivery');

-- --------------------------------------------------------

--
-- Structure de la table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE IF NOT EXISTS `ingredient` (
  `IdIngredient` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdIngredient`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Structure de la table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
CREATE TABLE IF NOT EXISTS `order_table` (
  `IdOrder` int(11) NOT NULL AUTO_INCREMENT,
  `IdCart` int(11) NOT NULL,
  `IdUser` int(11) NOT NULL,
  `IdRestaurant` int(11) NOT NULL,
  `OrderType` enum('delivery','no delivery') NOT NULL,
  `Status` enum('in progress','ready') NOT NULL,
  `OnlinePayment` tinyint(1) NOT NULL,
  `Amount` double NOT NULL,
  `DeliveryAddress` varchar(25) CHARACTER SET utf8 NOT NULL,
  `DeliveryTime` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdOrder`,`IdCart`,`IdUser`,`IdRestaurant`),
  KEY `restaurant_order_fk` (`IdRestaurant`),
  KEY `cart_order_fk` (`IdCart`,`IdUser`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `order_table`
--

INSERT INTO `order_table` (`IdOrder`, `IdCart`, `IdUser`, `IdRestaurant`, `OrderType`, `Status`, `OnlinePayment`, `Amount`, `DeliveryAddress`, `DeliveryTime`) VALUES
(1, 1, 2, 1, 'no delivery', 'in progress', 1, 16, 'none', '18h45');

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `IdProduct` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Description` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Price` decimal(10,0) NOT NULL,
  `IdCategory` int(11) NOT NULL,
  `IdRecipe` int(11) NOT NULL,
  PRIMARY KEY (`IdProduct`),
  KEY `recipe_product_fk` (`IdRecipe`),
  KEY `category_product_fk` (`IdCategory`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `receipeingredient`
--

DROP TABLE IF EXISTS `receipeingredient`;
CREATE TABLE IF NOT EXISTS `receipeingredient` (
  `IdRecipe` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `IdIngredient` int(11) NOT NULL,
  KEY `ingredient_receipeingredient_fk` (`IdIngredient`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Structure de la table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
CREATE TABLE IF NOT EXISTS `recipe` (
  `IdRecipe` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdRecipe`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `recipe`
--

INSERT INTO `recipe` (`IdRecipe`, `Name`) VALUES
(1, 'Reine'),
(2, 'Classique Jambon');

-- --------------------------------------------------------

--
-- Structure de la table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE IF NOT EXISTS `restaurant` (
  `IdRestaurant` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Address` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Schedule` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdRestaurant`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `restaurant`
--

INSERT INTO `restaurant` (`IdRestaurant`, `Name`, `Address`, `Schedule`) VALUES
(1, 'Pizzeria Evry', '20 rue ici', '10h-18h'),
(2, 'Pizzeria Grigny', '30 rue ici', '10h-18h'),
(3, 'Pizzeria Ris Orangis', '40 rue ici', '10h-18h');

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `IdStock` int(11) NOT NULL AUTO_INCREMENT,
  `Quantity` int(11) NOT NULL,
  `IdRestaurant` int(11) NOT NULL,
  `IdIngredient` int(11) NOT NULL,
  PRIMARY KEY (`IdStock`),
  KEY `ingredient_stock_fk` (`IdIngredient`),
  KEY `restaurant_stock_fk` (`IdRestaurant`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

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

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(25) CHARACTER SET utf8 NOT NULL,
  `LastName` varchar(25) CHARACTER SET utf8 NOT NULL,
  `LoginName` varchar(25) CHARACTER SET utf8 NOT NULL,
  `Password` varchar(25) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`IdUser`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

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
