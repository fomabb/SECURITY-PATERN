create table departments
(
    id              serial primary key,
    name            varchar(255),
    organization_id int,
    foreign key (organization_id) references organizations (id)
);