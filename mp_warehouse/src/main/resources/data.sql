INSERT INTO warehouse.mp_product (
    id,
    product_name,
    cost,
    description,
    category,
    brand,
    country,
    count,
    buyer_id,
    seller_id,
    warehouse_id
) VALUES
      ('1', 'Продукт 1', 100.50, 'Описание продукта 1',
       'Электроника',
       'Sony',
       'Россия',
       10,
       '2',
       'seller_id',
       'Main Warehouse')
