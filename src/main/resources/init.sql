CREATE TABLE Clients (
    code SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    credit_limit DECIMAL(10, 2),
    invoice_closing_day INT
);

CREATE TABLE Products (
    code SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    "value" DECIMAL(10, 2)
);

CREATE TABLE Sales (
    code SERIAL PRIMARY KEY,
    client_code INT REFERENCES Clients(code),
    sale_date DATE
);

CREATE TABLE ItensSale (
    sale_code INT REFERENCES Sales(code),
    product_code INT REFERENCES Products(code),
    quantity INT,
    PRIMARY KEY (sale_code, product_code)
);