CREATE TABLE auto_user (
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(30) NOT NULL ,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE auto_post (
    id           SERIAL PRIMARY KEY,
    text         TEXT NOT NULL ,
    created      TIMESTAMP,
    auto_user_id int REFERENCES auto_post (id)
);
