import psycopg2
from tqdm import tqdm

query_select_post_uuids = "select post_uuid from post"
query_select_comments = "select * from get_comments_by_post_uuid_sql(%s::uuid)"

conn = psycopg2.connect(
    host="localhost",
    database="demo",
    user="demo",
    password="changeme")
curr = conn.cursor()

curr.execute(query_select_post_uuids)
post_uuids = sorted([uuid for (uuid, ) in curr.fetchall()])

for post_uuid in tqdm(post_uuids):
    curr.execute(query_select_comments, (post_uuid,))
    comments = curr.fetchall()
    assert len(comments) == 100

curr.close()
conn.close()
