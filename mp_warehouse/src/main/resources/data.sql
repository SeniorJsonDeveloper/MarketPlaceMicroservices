INSERT INTO warehouse.products (id,product_name,price,count,warehouse_id)
VALUES
    (1,'Product_1',99.99,100,1),
    (2,'Product_2',49.50,500,1),
    (3,'Product_3',150.00,228,1),
    (4, 'Product_4',200.00,300,1),
    (5,'Product_5',19.99,420,1),
    (6,'Product_6',20.00,200,1),
    (7,'Product_7',21.00,400,1);

INSERT INTO warehouse.warehouses (id)
SELECT id FROM (VALUES (5), (6), (7), (8), (9)) AS vals(id)
WHERE NOT EXISTS (
    SELECT 1 FROM warehouse.warehouses w WHERE w.id = vals.id
);

INSERT INTO warehouse.products(warehouse_id)
VALUES (1),
       (2),
       (3),
       (4),
       (5)