-- +goose Up
create function get_comments_by_post_uuid_sql(uuid)
returns table(comment_uuid uuid, contents text, created_at timestamp, updated_at timestamp)
language sql
parallel safe
returns null on null input
as $$
    select comment_uuid, c.contents, c.created_at, c.updated_at
    from comment c
    join post p on c.post_id = p.post_id
    where p.post_uuid = $1
$$;
-- +goose Down
drop function get_comments_by_post_uuid_sql;
