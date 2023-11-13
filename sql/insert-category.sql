-- 大カテゴリー登録
INSERT INTO category (name)
    SELECT distinct SPLIT_PART(category_name, '/', 1) AS big_category
    FROM original
    WHERE category_name IS NOT NULL
ORDER BY big_category;

-- 中カテゴリー登録
INSERT INTO category (parent_id, name)
SELECT DISTINCT a.id,b.middle_category
FROM category a
INNER JOIN (
	SELECT
        DISTINCT SPLIT_PART(category_name, '/', 2) AS middle_category,
        SPLIT_PART(category_name, '/', 1) AS big_category
    FROM original
    WHERE category_name IS NOT NULL
) AS b
ON a.name = b.big_category;

-- 小カテゴリー登録
INSERT INTO category (name, name_all, parent_id)
SELECT b.small_category, b.category_name, a.middle_category_id
FROM (
	SELECT DISTINCT a.id, a.name AS big_category, b.id AS middle_category_id, b.parent_id, b.name AS middle_category
    FROM category a
    INNER JOIN category b
    ON a.id = b.parent_id
    ORDER BY id,middle_category_id
) AS a
INNER JOIN (
    SELECT 
        DISTINCT SPLIT_PART(category_name, '/', 3) AS small_category,
        SPLIT_PART(category_name, '/', 2) AS middle_category,
	    SPLIT_PART(category_name, '/', 1) AS big_category,
	    category_name
    FROM original
	WHERE category_name IS NOT NULL
) AS b
ON a.middle_category = b.middle_category AND a.big_category = b.big_category
ORDER BY category_name;

-- itemsテーブルに登録
INSERT INTO items (id,name,condition,category,brand,price,shipping,description)
SELECT distinct b.id ,b.name, b.condition_id AS condition, a.id AS category, b.brand,b.price,b.shipping,b.description
FROM category AS a
INNER JOIN (
	SELECT id, name, category_name, condition_id, brand,price,shipping,description
	FROM original
) AS b
ON a.name_all = b.category_name
ORDER BY b.id;
