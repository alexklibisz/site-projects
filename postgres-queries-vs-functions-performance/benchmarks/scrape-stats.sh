#!/bin/bash
set -e
echo "time,container,cpu_perc,mem_usage,net_io"
while true
do
    export TIME=$(date +%s)
    export STATS=$(docker stats --no-stream --format "${TIME},{{.Name}},{{.CPUPerc}},{{.MemUsage}},{{.NetIO}}" | grep postgres)
    echo $STATS
done
