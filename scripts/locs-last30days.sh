#!/usr/bin/env bash
rm -f ./output.json
curl -w "@curl-format.txt" -o ./output.json -s http://localhost:8080/last30days
LEN=$(cat ./output.json | jq length)
echo "Records $LEN"
