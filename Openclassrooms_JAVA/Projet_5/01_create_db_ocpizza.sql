
CREATE TABLE Ingredient (
                IdIngredient INT AUTO_INCREMENT NOT NULL,
                Name NVARCHAR(25) NOT NULL,
                PRIMARY KEY (IdIngredient)
);


CREATE TABLE Recipe (
                IdRecipe INT AUTO_INCREMENT NOT NULL,
                Name NVARCHAR(25) NOT NULL,
                PRIMARY KEY (IdRecipe)
);


CREATE TABLE ReceipeIngredient (
                IdRecipe INT NOT NULL,
                Quantity INT NOT NULL,
                IdIngredient INT NOT NULL
               
);


CREATE TABLE Category (
                IdCategory INT AUTO_INCREMENT NOT NULL,
                Name NVARCHAR(25) NOT NULL,
                Description NVARCHAR(25) NOT NULL,
                PRIMARY KEY (IdCategory)
);


CREATE TABLE Product (
                IdProduct INT AUTO_INCREMENT NOT NULL,
                Name NVARCHAR(25) NOT NULL,
                Description NVARCHAR(25) NOT NULL,
                Price  DECIMAL NOT NULL,
                IdCategory INT NOT NULL,
                IdRecipe INT NOT NULL,
                PRIMARY KEY (IdProduct)
);


CREATE TABLE Restaurant (
                IdRestaurant INT AUTO_INCREMENT NOT NULL,
                Name NVARCHAR(25) NOT NULL,
                Address NVARCHAR(25) NOT NULL,
                Schedule NVARCHAR(25) NOT NULL,
                PRIMARY KEY (IdRestaurant)
);


CREATE TABLE Stock (
                IdStock INT AUTO_INCREMENT NOT NULL,
                Quantity INT NOT NULL,
                IdRestaurant INT NOT NULL,
                IdIngredient INT NOT NULL,
                PRIMARY KEY (IdStock)
);


CREATE TABLE User (
                IdUser INT AUTO_INCREMENT NOT NULL,
                Name NVARCHAR(25) NOT NULL,
                LastName NVARCHAR(25) NOT NULL,
                LoginName NVARCHAR(25) NOT NULL,
                Password NVARCHAR(25) NOT NULL,
                
                PRIMARY KEY (IdUser)
);


CREATE TABLE Cart (
                IdCart INT AUTO_INCREMENT NOT NULL,
                IdUser INT NOT NULL,
                Quantity INT NOT NULL,
                PRIMARY KEY (IdCart, IdUser)
);


CREATE TABLE Cart_Product (
                IdCart INT NOT NULL,
                IdUser INT NOT NULL,
                IdProduct INT NOT NULL
               
);


CREATE TABLE Order_Table (
                IdOrder INT AUTO_INCREMENT NOT NULL,
                IdCart INT NOT NULL,
                IdUser INT NOT NULL,
                IdRestaurant INT NOT NULL,
                OrderType enum('delivery','no delivery') NOT NULL,
                Status enum('in progress','ready') NOT NULL,
                OnlinePayment BOOLEAN NOT NULL,
                Amount DOUBLE PRECISION NOT NULL,
                DeliveryAddress NVARCHAR(25) NOT NULL,
                DeliveryTime NVARCHAR(25) NOT NULL,
                PRIMARY KEY (IdOrder, IdCart, IdUser, IdRestaurant)
);

#ALTER TABLE Order_Table MODIFY COLUMN OrderType VARCHAR(25) COMMENT 'enum';

#ALTER TABLE Order_Table MODIFY COLUMN Status VARCHAR(25) COMMENT 'enum';


CREATE TABLE Bill (
                IdBill INT AUTO_INCREMENT NOT NULL,
                IdOrder INT NOT NULL,
                Amount DECIMAL NOT NULL,
                PaidStatus ENUM ('non payé','payé')NOT NULL,
		 
                PRIMARY KEY (IdBill, IdOrder)
);


CREATE TABLE Employe (
                IdUser INT NOT NULL,
                IdRestaurant INT NOT NULL,
                AccountType ENUM('seller', 'preparator', 'manager', 'delivery') NOT NULL,
                PRIMARY KEY (IdUser, IdRestaurant)
);


CREATE TABLE Client (
                IdUser INT NOT NULL,
                Adress NVARCHAR(25) NOT NULL,
                PRIMARY KEY (IdUser)
);


ALTER TABLE ReceipeIngredient ADD CONSTRAINT ingredient_receipeingredient_fk
FOREIGN KEY (IdIngredient)
REFERENCES Ingredient (IdIngredient)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Stock ADD CONSTRAINT ingredient_stock_fk
FOREIGN KEY (IdIngredient)
REFERENCES Ingredient (IdIngredient)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Product ADD CONSTRAINT recipe_product_fk
FOREIGN KEY (IdRecipe)
REFERENCES Recipe (IdRecipe)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ReceipeIngredient ADD CONSTRAINT recipe_receipeingredient_fk
FOREIGN KEY (IdRecipe)
REFERENCES Recipe (IdRecipe)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Product ADD CONSTRAINT category_product_fk
FOREIGN KEY (IdCategory)
REFERENCES Category (IdCategory)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Cart_Product ADD CONSTRAINT product_cart_product_fk
FOREIGN KEY (IdProduct)
REFERENCES Product (IdProduct)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Order_Table ADD CONSTRAINT restaurant_order_fk
FOREIGN KEY (IdRestaurant)
REFERENCES Restaurant (IdRestaurant)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Stock ADD CONSTRAINT restaurant_stock_fk
FOREIGN KEY (IdRestaurant)
REFERENCES Restaurant (IdRestaurant)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Employe ADD CONSTRAINT restaurant_employe_fk
FOREIGN KEY (IdRestaurant)
REFERENCES Restaurant (IdRestaurant)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Client ADD CONSTRAINT user_table_client_fk
FOREIGN KEY (IdUser)
REFERENCES User (IdUser)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Employe ADD CONSTRAINT user_employe_fk
FOREIGN KEY (IdUser)
REFERENCES User (IdUser)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Cart ADD CONSTRAINT user_cart_fk
FOREIGN KEY (IdUser)
REFERENCES User (IdUser)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Order_Table ADD CONSTRAINT cart_order_fk
FOREIGN KEY (IdCart, IdUser)
REFERENCES Cart (IdCart, IdUser)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Cart_Product ADD CONSTRAINT cart_cart_product_fk
FOREIGN KEY (IdCart, IdUser)
REFERENCES Cart (IdCart, IdUser)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Bill ADD CONSTRAINT order_bill_fk
FOREIGN KEY (IdOrder)
REFERENCES Order_Table (IdOrder)
ON DELETE NO ACTION
ON UPDATE NO ACTION;