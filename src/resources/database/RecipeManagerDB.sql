START TRANSACTION;

DROP TABLE IF EXISTS recipe, ingredient, collection, category, allergen CASCADE;

CREATE TABLE collection (
    id serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_collection PRIMARY KEY (id)
);

CREATE TABLE category (
    id serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_category PRIMARY KEY (id)
);

CREATE TABLE allergen (
    id serial NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT PK_allergen PRIMARY KEY (id)
);

CREATE TABLE recipe (
    id serial NOT NULL,
    name varchar(50) NOT NULL,
	collection_id int,
	category_id int NOT NULL,
    image_path varchar(250),
    CONSTRAINT PK_recipe PRIMARY KEY (id),
    CONSTRAINT FK_collection_recipe FOREIGN KEY (collection_id) REFERENCES collection (id),
    CONSTRAINT FK_category_recipe FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE ingredient (
    id serial NOT NULL,
    name varchar(50) NOT NULL,
	allergen_id int,
    image_path varchar(250),
    CONSTRAINT PK_ingredient PRIMARY KEY (id),
    CONSTRAINT FK_allergen_ingredient FOREIGN KEY (allergen_id) REFERENCES allergen (id)
);

CREATE TABLE ingredient_recipe (
    ingredient_id int NOT NULL,
    recipe_id int NOT NULL,
    CONSTRAINT PK_ingredient_recipe PRIMARY KEY (ingredient_id, recipe_id),
    CONSTRAINT FK_ingredient_recipe_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id),
    CONSTRAINT FK_ingredient_recipe_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

COMMIT;