#!/bin/bash
set -e
./scrape-stats.sh &
sleep 10
time python benchmark.py query
sleep 10
time python benchmark.py function
sleep 10
ps -x | grep '/bin/bash ./scrape-stats.sh' | grep -v grep | cut -d ' ' -f1 | xargs kill
python3 plot-stats.py
