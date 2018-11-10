CREATE TABLE person (
    person_id BIGINT PRIMARY KEY auto_increment,
    name VARCHAR(32),
    age INT,
);

CREATE TABLE skills (
    skill_id BIGINT PRIMARY KEY auto_increment,
    person_id BIGINT REFERENCES people (person_id),
    name VARCHAR(16),
    level VARCHAR(16)
);
