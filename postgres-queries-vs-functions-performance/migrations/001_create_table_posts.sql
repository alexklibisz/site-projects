-- +goose Up
create table post (
	post_id serial primary key,
	post_uuid uuid unique not null,
	contents text not null,
	created_at timestamp not null,
    updated_at timestamp
);
-- +goose Down
drop table post;
