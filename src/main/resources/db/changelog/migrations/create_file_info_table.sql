create table file_info
(
    file_id     bigint primary key auto_increment,
    checksum    varchar(255) not null,
    filename    varchar(255) not null,
    file_size   int          not null,
    upload_date datetime(6)  not null,
    user_usr_id bigint       null,
    foreign key (user_usr_id) references usr (usr_id)
);

