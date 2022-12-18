#!/bin/bash
set -e

docker-compose down --volumes
docker-compose up --wait
docker-compose down

docker-compose up --force-recreate --wait
./scrape-stats.sh > stats-query.csv &
sleep 1
time python benchmark.py query
sleep 1
ps -x | grep '/bin/bash ./scrape-stats.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
docker-compose down

docker-compose up --force-recreate --wait
./scrape-stats.sh > stats-function.csv &
sleep 1
time python benchmark.py function
sleep 1
ps -x | grep '/bin/bash ./scrape-stats.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
docker-compose down

python3 plot-stats.py
