-- This disables parallel gathers, as I've found they produce highly
-- variable results depending on the host system.
set max_parallel_workers_per_gather = 0;

-- This makes it more likely that the query planner chooses an index scan
-- instead of another strategy. I've found this will generally improve
-- performance on any system with an SSD.
set random_page_cost = 0.9;

select
   'abc' ilike '%' || 'ab' || '%' as "abc contains ab",
   'abc' ilike '%' || 'AB' || '%' as "abc contains AB",
   'abc' ilike '%' || 'abc' || '%' as "abc contains abc",
   'abc' ilike '%' || 'abb' || '%' as "abc contains abb"
;

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q)
select review_id, 1.0 as score
from reviews, input
where summary ilike '%' || input.q || '%'
limit 10;

explain (analyze, buffers)
with input as (select 'Michaelllll Lewis' as q) -- This string doesn't exist.
select review_id, 1.0 as score
from reviews, input
where summary ilike '%' || input.q || '%'
limit 10;

with input as (select 'abc' as text1, 'abb' as text2)
select
  show_trgm(text1) as "text1 trigrams",
  show_trgm(text2) as "text2 trigrams",
  array(select t1.t1 from unnest(show_trgm(text1)) t1, unnest(show_trgm(text2)) t2 where t1.t1 = t2.t2) as "intersection",
  array(select t1.t1 from unnest(show_trgm(text1)) t1 union select t2.t2 from unnest(show_trgm(text2)) t2) as "union",
  similarity(text1, text2)
from input;

explain (analyze, buffers)
with input as (select 'Michael Louis' as q)
select review_id, summary, 1.0 - (input.q <-> summary) as score
from reviews, input
where input.q % summary
order by input.q <-> summary
limit 10;

explain (analyze, buffers)
with input as (select 'Michael Lewis' as q)
select review_id, summary, 1.0 - (input.q <-> summary) as score
from reviews, input
where input.q % summary
order by input.q <-> summary
limit 10;

---

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
