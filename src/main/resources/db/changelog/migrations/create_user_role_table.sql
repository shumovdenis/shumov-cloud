create table user_role
(
    user_id bigint       not null,
    roles   varchar(255) null,
    constraint FKfpm8swft53ulq2hl11yplpr5
        foreign key (user_id) references usr (usr_id)
);