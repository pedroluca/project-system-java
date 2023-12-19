-- Criação do Banco de Dados
CREATE DATABASE sisteminha_produtos_2;
USE sisteminha_produtos_2;

-- Produto, Cliente, Transacao, Item_Transacao, Caixa, 
-- Historico talvez não precise, pois a tabela Item_Transacao já faz isso de forma indireta

-- Criação das Tabelas
CREATE TABLE Produto (
  codigo VARCHAR(10) PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  preco DECIMAL(10,2) NOT NULL,
  quantidade INT NOT NULL,
  data_vencimento DATE,
  material VARCHAR(100),
  isPerecivel BOOLEAN NOT NULL
);

CREATE TABLE Cliente (
  cpf VARCHAR(11) PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  idade INT NOT NULL
);

CREATE TABLE Transacao (
  codigo INT PRIMARY KEY AUTO_INCREMENT,
  cpf_cliente VARCHAR(11),
  data_transacao DATE NOT NULL,
  valor_total DECIMAL(10,2) NOT NULL,
  isVenda BOOLEAN NOT NULL,
  FOREIGN KEY (cpf_cliente) REFERENCES Cliente(cpf)
);

CREATE TABLE Item_Transacao (
  codigo_produto VARCHAR(10) NOT NULL,
  codigo_transacao INT AUTO_INCREMENT,
  quantidade_produto INT NOT NULL,
  preco_produto DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (codigo_transacao, codigo_produto),
  FOREIGN KEY (codigo_transacao) REFERENCES Transacao(codigo),
  FOREIGN KEY (codigo_produto) REFERENCES Produto(codigo)
);

CREATE TABLE Caixa (
  id INT PRIMARY KEY AUTO_INCREMENT,
  saldo DECIMAL(10,2) NOT NULL
);

-- Inserção de Primeiros dados
INSERT INTO Cliente (nome, cpf, idade) VALUES ('Pedro', '12312312312', 20);
INSERT INTO Cliente (nome, cpf, idade) VALUES ('Lucas', '12332112312', 19);
INSERT INTO Cliente (nome, cpf, idade) VALUES ('João', '12312332112', 20);
INSERT INTO Cliente (nome, cpf, idade) VALUES ('Tharlis', '12332132112', 18);
INSERT INTO Produto (codigo, nome, preco, quantidade, data_vencimento, isPerecivel) VALUES ('0001', 'Feijão', 5.7, 7, '2024-05-20', 1);
INSERT INTO Produto (codigo, nome, preco, quantidade, data_vencimento, isPerecivel) VALUES ('0002', 'Arroz', 3.4, 8, '2024-05-20', 1);
INSERT INTO Produto (codigo, nome, preco, quantidade, data_vencimento, isPerecivel) VALUES ('0003', 'Macarrão', 7.34, 20, '2024-05-20', 1);
INSERT INTO Produto (codigo, nome, preco, quantidade, data_vencimento, isPerecivel) VALUES ('0004', 'Tapioca', 15.99, 12, '2024-05-20', 1);
INSERT INTO Produto (codigo, nome, preco, quantidade, data_vencimento, isPerecivel) VALUES ('0005', 'Banana', 7.89, 5, '2024-05-20', 1);
INSERT INTO Produto (codigo, nome, preco, quantidade, material, isPerecivel) VALUES ('0006', 'Martelo', 39.99, 10, 'Madeira e ferro', 0);
INSERT INTO Caixa (saldo) VALUES (0);