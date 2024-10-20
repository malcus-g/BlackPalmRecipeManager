-- database name: RecipeManagerDB
BEGIN TRANSACTION;

-- Drop all db objects in the proper order
DROP TABLE IF EXISTS users, instruction, ingredient_recipe, recipe, ingredient, collection, category, allergen CASCADE;
-- Create the tables and constraints

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

--collection
CREATE TABLE collection (
    id SERIAL,
    name varchar(50) NOT NULL,
    image_path varchar(250),
    CONSTRAINT PK_collection PRIMARY KEY (id)
);

--category
CREATE TABLE category (
    id SERIAL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_category PRIMARY KEY (id)
);

--allergen
CREATE TABLE allergen (
    id SERIAL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_allergen PRIMARY KEY (id)
);

--recipe
CREATE TABLE recipe (
    id SERIAL,
    name varchar(50) NOT NULL,
	collection_id int,
	category_id int NOT NULL,
    image_path varchar(250),
    CONSTRAINT PK_recipe PRIMARY KEY (id),
    CONSTRAINT FK_collection_recipe FOREIGN KEY (collection_id) REFERENCES collection (id),
    CONSTRAINT FK_category_recipe FOREIGN KEY (category_id) REFERENCES category (id)
);

--ingredient
CREATE TABLE ingredient (
    id SERIAL,
    name varchar(50) NOT NULL,
	allergen_id int,
    image_path varchar(250),
    CONSTRAINT PK_ingredient PRIMARY KEY (id),
    CONSTRAINT FK_allergen_ingredient FOREIGN KEY (allergen_id) REFERENCES allergen (id)
);

--instruction
CREATE TABLE instruction (
    recipe_id int NOT NULL,
	step_number int NOT NULL,
	step_text varchar(1000) NOT NULL,
	CONSTRAINT PK_instruction PRIMARY KEY (recipe_id, step_number),
	CONSTRAINT FK_recipe_instruction FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

--ingredient_recipe
CREATE TABLE ingredient_recipe (
    ingredient_id int NOT NULL,
    recipe_id int NOT NULL,
    measurement_unit varchar(10),
    quantity decimal(6,2),
    CONSTRAINT PK_ingredient_recipe PRIMARY KEY (ingredient_id, recipe_id),
    CONSTRAINT FK_ingredient_recipe_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id),
    CONSTRAINT FK_ingredient_recipe_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

-- Insert some sample starting data

-- Users
    -- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN');

--Collections
INSERT INTO
    collection (name)
VALUES
    ('Miscellaneous'),
    ('Summer Collection'),
    ('Fall Collection'),
    ('Winter Collection'),
    ('Spring Collection');

--Categories
INSERT INTO
    category (name)
VALUES
    ('Cookies'),
    ('Bars & Brownies'),
    ('Cupcakes'),
    ('Cakes'),
    ('Buttercreams & Icings'),
    ('Muffins');

--Allergens
INSERT INTO
    allergen (name)
VALUES
    ('None'),
    ('Tree Nuts'),
    ('Peanuts'),
    ('Gluten'),
    ('Dairy'),
    ('Wheat');

--Recipes
INSERT INTO
    recipe (name, category_id, collection_id, image_path)
VALUES
    ('Vanilla Bean Cut Out Sugar Cookie', 1, 1, 'sugar_cookies.jpg'),
    ('Salted Brown Butter Chocolate Chunk Cookie', 1, 1, 'chocolate_chip_cookie.jpg');

INSERT INTO
    recipe (name, category_id, image_path)
VALUES
    ('Brownie', 2, 'brownie.jpg'),
    ('Chocolate Cupcake', 3, 'chocolate_cupcake.jpg'),
    ('Red Velvet Cake', 4, 'red_velvet_cake.jpg'),
    ('Buttercream', 5, 'buttercream.jpg'),
    ('Blueberry Muffin', 6, 'blueberry_muffin.jpg');

--Ingredients
INSERT INTO
    ingredient (name)
VALUES
    ('Sugar'),
    ('Egg Yolks'),
    ('Sour Cream'),
    ('Vanilla Mixture'),
    ('Flour'),
    ('Baking Powder'),
    ('Salt'),
    ('Brown Sugar'),
    ('Granulated Sugar'),
    ('Eggs'),
    ('Vanilla'),
    ('AP Flour'),
    ('Baking Soda'),
    ('Chocolate Chips');

INSERT INTO
    ingredient (name, allergen_id)
VALUES
    ('Butter', 4),
    ('Salted Butter', 4);

--Instructions
INSERT INTO
    instruction (recipe_id, step_number, step_text)
VALUES
    --Vanilla Bean Cut Out Sugar Cookie
    (1, 1, 'Mix butter and sugar together on speed 1 for 1 minute and then on speed 2 for 1 minute.'),
    (1, 2, 'Add sour cream, egg yolks, and vanilla to the mixing bowl. Mix on speed 1 for 45 seconds and speed 2 for 45 seconds (mixture should look kind of like puke lol).'),
    (1, 3, 'Add flour, baking powder, and salt in two parts and mix on speed 1 until flour is not visible. If dough is sticky, add small bits of flour and mix.'),
    (1, 4, 'On a floured surface, roll the dough to 3/8 of an inch thick.'),
    (1, 5, 'Cut desired shapes and add to sheet pan. Do not overcrowd the pan!! 12-15 cookies per tray.'),
    (1, 6, 'Bake for 12 minutes at 300 degrees (convection oven) or 350 degrees (conduction oven). Rotate cookies at the 6 minute mark.'),

    --Salted Brown Butter Chocolate Chunk Cookie
    (2, 1, 'Preheat the oven to 350 degrees.'),
    (2, 2, 'Cream butter and both sugars in the mixing bowl. Mix on speed 1 for 1 minute and then on speed 2 for 1 minute (or until all parts are combined).'),
    (2, 3, 'Add in eggs and vanilla. Mix on speed 1 for 30 seconds and then on speed 2 for 30 seconds.'),
    (2, 4, 'Add flour, baking soda, salt and baking powder and mix together. Mix on speed 1 for 1 minute. Or until no flour is visible.'),
    (2, 5, 'Add in chocolate bits.'),
    (2, 6, 'Scoop dough using yellow cookie scoop (3x5).'),
    (2, 7, 'Slightly press cookies down to assist with spreading.'),
    (2, 8, 'Bake for 6 minutes, rotate, then bake for another 5 minutes.');

--Recipe Ingredients
INSERT INTO
    ingredient_recipe (ingredient_id, recipe_id, measurement_unit, quantity)
VALUES
    --Vanilla Bean Cutout Sugar Cookie
    (1, 1, 'lb', 1.00),
    (3, 1, 'g', 532.00),
    (2, 1, '', 8.00),
    (4, 1, 'g', 120.00),
    (5, 1, 'oz', 2.00),
    (6, 1, 'g', 960.00),
    (7, 1, 'tsp', 1),
    (15, 1, 'tsp', 1),

    --Salted Brown Butter Chocolate Chunk Cookie
    (16, 2, 'lb', .50),
    (8, 2, 'g', 320.00),
    (9, 2, 'g', 100.00),
    (10, 2, '', 2.00),
    (11, 2, 'tbsp', 1.33),
    (12, 2, 'g', 426.00),
    (6, 2, 'tsp', 1.00),
    (7, 2, 'tsp', 2.00),
    (13, 2, 'tsp', 1.00),
    (14, 2, 'g', 339.00);

COMMIT TRANSACTION;
