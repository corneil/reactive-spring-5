@echo off
del /Q ext-output.json
curl -w "@curl-format.txt" -o ext-output.json -s http://localhost:8080/extlast30days
