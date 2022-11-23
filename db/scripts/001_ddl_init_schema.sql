create TABLE auto_user (
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(30) NOT NULL UNIQUE ,
    password VARCHAR(30) NOT NULL
);

create TABLE auto_post (
    id           SERIAL PRIMARY KEY,
    text         TEXT NOT NULL ,
    created      TIMESTAMP NOT NULL,
    user_id INT NOT NULL REFERENCES auto_user (id)
);
