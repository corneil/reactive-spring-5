@echo off
del /Q output.json
curl -w "@curl-format.txt" -o output.json -s http://localhost:8080/last30days
