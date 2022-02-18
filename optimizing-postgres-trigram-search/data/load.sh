#!/bin/bash
set -e

pg_isready -h $PGHOST -p $PGPORT || sleep 5
pg_isready -h $PGHOST -p $PGPORT || sleep 5
pg_isready -h $PGHOST -p $PGPORT

time psql -v ON_ERROR_STOP=1 <<END_OF_SQL
create extension if not exists pg_trgm;
create unlogged table if not exists reviews (
  review_id bigserial primary key,
  reviewer_id varchar(50),
  reviewer_name varchar(100),
  asin varchar(50),
  summary varchar(1000)
);
\copy reviews from program 'python3 /mnt/data/preprocess.py /mnt/data/reviews_Books_5.json.gz' CSV HEADER;
update reviews set reviewer_id = null where reviewer_id = '';
update reviews set reviewer_name = null where reviewer_name = '';
update reviews set asin = null where asin = '';
update reviews set summary = null where summary = '';
select count(*) from reviews;
END_OF_SQL
