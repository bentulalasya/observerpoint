DROP TABLE IF EXISTS departments;
CREATE TABLE departments
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name varchar(1000) NOT NULL
);

DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(
    employeeId int AUTO_INCREMENT PRIMARY KEY,
    name       varchar(1000) NOT NULL,
    id         int,
    foreign key (id) references departments (id)
);
