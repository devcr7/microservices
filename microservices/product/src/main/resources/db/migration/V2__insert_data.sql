-- Insert data into the category table
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES (1, 'Motherboards', 'Circuit boards that connect all components of a computer');
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES (2, 'Processors', 'Central processing units (CPUs) that perform calculations and tasks');
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES (3, 'Memory', 'RAM modules that store data temporarily for quick access');
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES (4, 'Storage', 'Devices like SSDs and HDDs that store data permanently');
INSERT INTO CATEGORY (ID, NAME, DESCRIPTION) VALUES (5, 'Graphics Cards', 'Hardware that renders images and video for display');

-- Insert data into the product table
INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE, AVAILABLE_QUANTITY, CATEGORY_ID) VALUES (1, 'ASUS ROG Strix B550-F', 'Gaming Motherboard', 199.99, 50, 1);
INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE, AVAILABLE_QUANTITY, CATEGORY_ID) VALUES (2, 'Intel Core i9-11900K', '8-Core Processor', 549.99, 30, 2);
INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE, AVAILABLE_QUANTITY, CATEGORY_ID) VALUES (3, 'Corsair Vengeance LPX 16GB', 'DDR4 Memory Kit', 89.99, 100, 3);
INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE, AVAILABLE_QUANTITY, CATEGORY_ID) VALUES (4, 'Samsung 970 EVO Plus 1TB', 'NVMe M.2 SSD', 149.99, 75, 4);
INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE, AVAILABLE_QUANTITY, CATEGORY_ID) VALUES (5, 'NVIDIA GeForce RTX 3080', 'Graphics Card', 699.99, 20, 5);