from tqdm import tqdm
import psycopg2
import sys

assert len(sys.argv) == 2 and sys.argv[1] in {'query', 'function'}, "usage: python3 benchmark.py (query|function)"
query_or_function = sys.argv[1]

query_select_post_uuids = "select post_uuid from post"
if query_or_function == 'query':
    query_select_comments = """
        select count(comment_id)
        from comment c
        join post p on c.post_id = p.post_id
        where p.post_uuid = %s::uuid
    """
else:
    query_select_comments = "select * from comment_count_by_post_uuid(%s::uuid)"

conn = psycopg2.connect(
    host="localhost",
    database="demo",
    user="demo",
    password="changeme")
curr = conn.cursor()

curr.execute(query_select_post_uuids)
post_uuids = sorted([uuid for (uuid, ) in curr.fetchall()])

for post_uuid in tqdm(post_uuids, desc=query_or_function, bar_format='{l_bar}{bar:10}{r_bar}{bar:-10b}'):
    curr.execute(query_select_comments, (post_uuid,))
    comment_counts = curr.fetchone()

curr.close()
conn.close()
