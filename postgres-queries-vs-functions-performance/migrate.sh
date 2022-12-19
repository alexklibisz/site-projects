#!/bin/sh
set -e
goose postgres "host=postgres user=demo password=changeme dbname=demo sslmode=disable" up
touch /tmp/ready.txt
tail -f /dev/null
