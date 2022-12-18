-- +goose Up
insert into comment(comment_uuid, post_id, contents, created_at, updated_at)
select gen_random_uuid(),
       post_id,
       md5(post_id::text || comment_number::text),
       now(),
       null
from post
cross join generate_series(1, 100) as comment_number;
-- +goose Down
delete from comment;
