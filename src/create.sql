CREATE TABLE veiculos (
    id number GENERATED always as identity,
    marca varchar(200),
    modelo varchar(200),
    ano integer,
    preco number(9,2)
)