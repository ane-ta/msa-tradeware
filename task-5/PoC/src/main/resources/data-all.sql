-- Заливаем данные
DELETE FROM products;

INSERT INTO loyality_data (productSku, loyalityData)
VALUES 
    ('20001', 'Loyality_on'),
    ('30001', 'Loyality_on'),
    ('50001', 'Loyality_on'),
    ('60001', 'Loyality_on')
ON CONFLICT (productsku) 
DO UPDATE SET loyalityData = EXCLUDED.loyalityData; 