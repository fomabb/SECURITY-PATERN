create table employees
(
    id              serial primary key,
    first_name      varchar(50),
    last_name       varchar(50),
    position        varchar(50),
    salary          int,
    age             int,
    department_id   int,
    organization_id int,
    foreign key (department_id) references departments (id),
    foreign key (organization_id) references organizations (id)
);