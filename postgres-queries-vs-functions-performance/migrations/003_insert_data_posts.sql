-- +goose Up
insert into post(post_uuid, contents, created_at, updated_at)
select gen_random_uuid(),
       md5(post_number::text),
       now(),
       null
from generate_series(1, 100000) post_number;

-- +goose Down
delete from post;
