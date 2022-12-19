-- +goose Up
create table comment (
    comment_id serial primary key,
    comment_uuid uuid unique not null,
    post_id serial references post(post_id),
    contents text not null,
    created_at timestamp not null,
    updated_at timestamp
);

create index comment_post_id_idx on comment(post_id);

-- +goose Down
drop table comment;
drop index comment_post_id_idx;
