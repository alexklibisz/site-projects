#!/bin/bash
set -e

docker-compose down --volumes
docker-compose up --wait
docker-compose down

docker-compose up --force-recreate --wait
./scrape-metrics.sh > metrics-query.csv &
sleep 1
time python benchmark.py query
sleep 1
ps -x | grep '/bin/bash ./scrape-metrics.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
docker-compose down

docker-compose up --force-recreate --wait
./scrape-metrics.sh > metrics-function.csv &
sleep 1
time python benchmark.py function
sleep 1
ps -x | grep '/bin/bash ./scrape-metrics.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
docker-compose down

python3 plot-metrics.py
