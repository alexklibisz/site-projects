-- +goose Up
create function comment_count_by_post_uuid(uuid)
returns integer
language sql
parallel safe
returns null on null input
as $$
    select count(comment_id)
    from comment c
    join post p on c.post_id = p.post_id
    where p.post_uuid = $1
$$;
-- +goose Down
drop function comment_count_by_post_uuid;
