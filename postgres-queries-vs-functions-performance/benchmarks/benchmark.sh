#!/bin/bash
set -e
./scrape-stats.sh > stats-function.csv &
sleep 10
for i in {1..3}
do
    time python benchmark.py function
    sleep 5
done
ps -x | grep '/bin/bash ./scrape-stats.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
python3 plot-stats.py "function"

./scrape-stats.sh > stats-query.csv &
for i in {1..3}
do
    time python benchmark.py function
    sleep 5
done
ps -x | grep '/bin/bash ./scrape-stats.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
python3 plot-stats.py "query"

