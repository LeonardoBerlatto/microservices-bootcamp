CREATE TABLE "user"(
    id SERIAL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(30) UNIQUE NOT NULL,
    "password" VARCHAR(128) NOT NULL
);

ALTER TABLE "user" ADD CONSTRAINT pk_user PRIMARY KEY(id);