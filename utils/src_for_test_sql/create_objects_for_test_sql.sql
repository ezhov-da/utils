--создание таблицы для тестирования выборки
create table TEST_SELECT
(
     ID             int IDENTITY(1,1)
    ,PRODUCT_NAME   varchar(20)
    ,PRICE          decimal(18,6)       
)
--наполняем таблицу для тестирования выборки
insert into TEST_SELECT (PRODUCT_NAME, PRICE)
values
('помидор',23.45),
('картофель',12.1),
('лук',19.45),
('чеснок',12.15),
('баклажан',56.78),
('капуста',14.48),
('сельдерей',16.78),
('яблоко',16.19),
('банан',1.006)
--проверяем внесение
select * from TEST_SELECT
--==============================================================================
--создание таблицы для тестирования внесения
create table TEST_INSERT
(
     ID             int IDENTITY(1,1)
    ,PRODUCT_NAME   varchar(20)
    ,PRICE          decimal(18,6)       
)
--наполняем таблицу для тестирования выборки
insert into TEST_INSERT (PRODUCT_NAME, PRICE) values (?,?)
--проверяем внесение
select * from TEST_INSERT

