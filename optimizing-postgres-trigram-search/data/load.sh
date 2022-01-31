#!/bin/bash
set -e

pg_isready -h $PGHOST -p $PGPORT || sleep 5
pg_isready -h $PGHOST -p $PGPORT || sleep 5
pg_isready -h $PGHOST -p $PGPORT

# zcat $1 | head -n1000000 | grep -v '\\' | jq -c '{reviewerID,reviewerName,asin,summary}'
# psql -v ON_ERROR_STOP=1 <<END_OF_SQL
# create unlogged table raw_json (doc json);
# \copy raw_json from program '/mnt/data/preprocess.sh /mnt/data/reviews_Books_5.json.gz';
# create unlogged table reviews as
# select
#   (doc ->> 'reviewerID')::varchar(50) as reviewer_id,
#   (doc ->> 'reviewerName')::varchar(100) as reviewer_name,
#   (doc ->> 'asin')::varchar(50) as asin,
#   (doc ->> 'summary')::varchar(1000) as summary
# from raw_json;
# select count(*) from reviews;
# drop table raw_json;
# END_OF_SQL

psql -v ON_ERROR_STOP=1 <<END_OF_SQL
create extension pg_trgm;
create unlogged table reviews (
  review_id bigserial primary key,
  reviewer_id varchar(50),
  asin varchar(50),
  reviewer_name varchar(100),
  summary varchar(1000)
);
\copy reviews from program 'python3 /mnt/data/preprocess.py /mnt/data/reviews_Books_5.json.gz' CSV HEADER;
select count(*) from reviews;
END_OF_SQL