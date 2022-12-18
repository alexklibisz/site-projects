-- +goose Up
create table post (
	post_id serial PRIMARY KEY,
	post_uuid uuid UNIQUE NOT NULL,
	contents TEXT NOT NULL,
	created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
-- +goose Down
drop table post;
