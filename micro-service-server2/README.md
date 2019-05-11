# Introduction

# startup dependencies
redis & cassandra & rabbitmq

# cassandra script
CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE mykeyspace;

CREATE TABLE customers (
  customerId uuid PRIMARY KEY,
  firstName text,
  lastName text
);

INSERT INTO customers (customerId,  firstName, lastName) VALUES (c185d7a8-98fd-4c52-9274-6920300bbd0a, 'john', 'smith');
INSERT INTO customers (customerId,  firstName, lastName) VALUES (b6e51acd-b21f-4934-88a8-36a0dfcfdc3b, 'john', 'doe');
INSERT INTO customers (customerId,  firstName, lastName) VALUES (5127697b-1c12-4e0e-a70a-a23c1be65781, 'john', 'smith');

CREATE INDEX ON customers (lastName);

SELECT * FROM customers WHERE lastName = 'smith';