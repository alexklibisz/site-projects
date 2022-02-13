-- This disables parallel gathers, as I've found they produce highly
-- variable results depending on the host system.
set max_parallel_workers_per_gather = 0;

-- This makes it more likely that the query planner chooses an index scan
-- instead of another strategy. I've found this will generally improve
-- performance on any system with an SSD.
set random_page_cost = 0.9;

with input as (select 'abc' as text1, 'abb' as text2)
select
  show_trgm(text1) as "text1 trigrams",
  show_trgm(text2) as "text2 trigrams",
  array(select t1.t1
        from unnest(show_trgm(text1)) t1,
             unnest(show_trgm(text2)) t2 where t1.t1 = t2.t2) as "intersection",
  array(select t1.t1 from unnest(show_trgm(text1)) t1
        union
        select t2.t2 from unnest(show_trgm(text2)) t2) as "union",
  round(similarity(text1, text2)::numeric, 3) as "similarity",
  text1 % text2 as "text1 % text2",
  text1 <-> text2 as "text1 <-> text2"
from input;

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q) -- (1)
select review_id,
       1.0 - (summary <-> input.q) as score -- (4)
from reviews, input
where input.q % summary -- (2)
order by input.q <-> summary limit 10; -- (3)

explain (analyze, buffers)
with input as (select 'Michael Louis' as q) -- (1)
select review_id,
       1.0 - (summary <-> input.q) as score -- (4)
from reviews, input
where input.q % summary -- (2)
order by input.q <-> summary limit 10; -- (3)

create index reviews_summary_trgm_gist_idx on reviews
  using gist(summary gist_trgm_ops(siglen=64));
vacuum analyze reviews;

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q) -- (1)
select review_id,
       1.0 - (summary <-> input.q) as score -- (4)
from reviews, input
where input.q % summary -- (2)
order by input.q <-> summary limit 10; -- (3)

explain (analyze, buffers)
with input as (select 'Michael Louis' as q) -- (1)
select review_id,
       1.0 - (summary <-> input.q) as score -- (4)
from reviews, input
where input.q % summary -- (2)
order by input.q <-> summary limit 10; -- (3)

drop index reviews_summary_trgm_gist_idx;
create index reviews_summary_trgm_gist_idx on reviews
  using gist(summary gist_trgm_ops(siglen=256));
vacuum analyze reviews;

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q) -- (1)
select review_id,
       1.0 - (summary <-> input.q) as score -- (4)
from reviews, input
where input.q % summary -- (2)
order by input.q <-> summary limit 10; -- (3)

explain (analyze, buffers)
with input as (select 'Michael Louis' as q) -- (1)
select review_id,
       1.0 - (summary <-> input.q) as score -- (4)
from reviews, input
where input.q % summary -- (2)
order by input.q <-> summary limit 10; -- (3)

-- Table and index sizes thanks to:
-- https://gist.github.com/kevinjom/628bac642a424b8b7ca43ed77171b506
SELECT
    t.tablename,
    indexname,
    c.reltuples AS num_rows,
    pg_size_pretty(pg_relation_size(quote_ident(t.tablename)::text)) AS table_size,
    pg_size_pretty(pg_relation_size(quote_ident(indexrelname)::text)) AS index_size,
    CASE WHEN indisunique THEN 'Y'
       ELSE 'N'
    END AS UNIQUE,
    idx_scan AS number_of_scans,
    idx_tup_read AS tuples_read,
    idx_tup_fetch AS tuples_fetched
FROM pg_tables t
LEFT OUTER JOIN pg_class c ON t.tablename=c.relname
LEFT OUTER JOIN
    ( SELECT c.relname AS ctablename, ipg.relname AS indexname, x.indnatts AS number_of_columns, idx_scan, idx_tup_read, idx_tup_fetch, indexrelname, indisunique FROM pg_index x
           JOIN pg_class c ON c.oid = x.indrelid
           JOIN pg_class ipg ON ipg.oid = x.indexrelid
           JOIN pg_stat_all_indexes psai ON x.indexrelid = psai.indexrelid )
    AS foo
    ON t.tablename = foo.ctablename
WHERE t.schemaname='public'
ORDER BY 1,2;

--- JUNK ---
SHOW ALL;

select * from reviews

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q) -- (1)
select review_id, reviewer_id, reviewer_name, summary, asin,
       (1 - least(
        input.q <-> reviewer_id,
        input.q <-> reviewer_name,
        input.q <-> summary,
        input.q <-> asin)) as score -- (4)
from reviews, input
where input.q % reviewer_id -- (2)
   or input.q % reviewer_name
   or input.q % summary
   or input.q % asin
order by least(
    input.q <-> reviewer_id,
    input.q <-> reviewer_name,
    input.q <-> summary,
    input.q <-> asin)
limit 10; -- (3)

select
   'abc' ilike '%' || 'ab' || '%' as "abc contains ab",
   'abc' ilike '%' || 'AB' || '%' as "abc contains AB",
   'abc' ilike '%' || 'abc' || '%' as "abc contains abc",
   'abc' ilike '%' || 'abb' || '%' as "abc contains abb";

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q)
select review_id, 1.0 as score
from reviews, input
where summary ilike '%' || input.q || '%'
limit 10;

explain (analyze, buffers)
with input as (select 'Michael Louis' as q)
select review_id, 1.0 as score
from reviews, input
where summary ilike '%' || input.q || '%'
limit 10;


with input as (select 'kindel' as q)
select review_id,
       1 - least(
       input.q <<-> reviewer_id,
       input.q <<-> asin,
       input.q <<-> reviewer_name,
       input.q <<-> summary
       ) as score
from reviews, input
where input.q <% reviewer_id
   or input.q <% asin
   or input.q <% reviews.reviewer_name
   or input.q <% summary
order by
    least(
       input.q <<-> reviewer_id,
       input.q <<-> asin,
       input.q <<-> reviewer_name,
       input.q <<-> summary
    )
limit 100;


create index reviews_reviewer_id_trgm_gist_idx on reviews using gist(reviewer_id gist_trgm_ops);
create index reviews_asin_trgm_gist_idx on reviews using gist(asin gist_trgm_ops);
create index reviews_reviewer_name_trgm_gist_idx on reviews using gist(reviewer_name gist_trgm_ops);
create index reviews_summary_trgm_gist_idx on reviews using gist(summary gist_trgm_ops);

explain analyze
with input as (select 'kindel' as q)
select review_id,
       1 - least(
       input.q <<-> reviewer_id,
       input.q <<-> asin,
       input.q <<-> reviewer_name,
       input.q <<-> summary
       ) as score
from reviews, input
where input.q <% reviewer_id
   or input.q <% asin
   or input.q <% reviews.reviewer_name
   or input.q <% summary
order by
    least(
       input.q <<-> reviewer_id,
       input.q <<-> asin,
       input.q <<-> reviewer_name,
       input.q <<-> summary
    )
limit 100;

with input as (select 'kindel' as q)
select review_id,
       1 - (input.q <<-> asin) as score
from reviews, input
where input.q <% asin
order by input.q <<-> asin
limit 100;

explain analyse
with input as (select 'kindle' as q)
select review_id,
       1 - (input.q <<-> asin) as score
from reviews, input
where input.q <% asin
order by input.q <<-> asin
limit 100;

explain analyse
with input as (select 'kindle' as q)
select review_id, 1.0 as score
from reviews, input
where asin ilike '%' || input.q || '%'
limit 100;
