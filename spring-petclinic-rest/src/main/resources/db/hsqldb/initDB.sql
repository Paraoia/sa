DROP TABLE students IF EXISTS;

CREATE TABLE students (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  major      VARCHAR(30),
  usual_mark DOUBLE,
  prac_mark  DOUBLE,
  final_mark DOUBLE,
  major_mark DOUBLE,
);
CREATE INDEX students_last_name ON students (last_name);
