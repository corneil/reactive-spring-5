#!/usr/bin/env bash
rm -f ./output.json
curl -w "@curl-format.txt" -o ./ext-output.json -s http://localhost:8080/extlast30days
LEN=$(cat ./ext-output.json | jq length)
echo "Records $LEN"
