
    create table T_BOOK (
        nb_of_pages integer,
        price numeric(38,2),
        publication_date date,
        id bigserial not null,
        isbn varchar(50),
        title varchar(100),
        description varchar(10000),
        image_url varchar(255),
        primary key (id)
    );
