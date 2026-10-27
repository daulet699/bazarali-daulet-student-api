#!/usr/bin/env bash
set -euo pipefail
BASE="${1:-http://localhost:8092}"
BODY='{"fullName":"Daulet Bazarali","email":"daulet@example.com","groupCode":"IT2-2308","studentNumber":"37392"}'
printf 'Health check: '
curl --fail --silent "$BASE/actuator/health" && echo
printf '\nCREATE student:\n'
RESPONSE="$(curl --fail --silent -X POST "$BASE/api/students" -H 'Content-Type: application/json' -d "$BODY")"
printf '%s\n' "$RESPONSE"
ID="$(printf '%s' "$RESPONSE" | python3 -c 'import json,sys; print(json.load(sys.stdin)["id"])')"
printf '\nLIST students:\n'
curl --fail --silent "$BASE/api/students" && echo
printf '\nGET student ID %s:\n' "$ID"
curl --fail --silent "$BASE/api/students/$ID" && echo
printf '\nUPDATE student ID %s:\n' "$ID"
curl --fail --silent -X PUT "$BASE/api/students/$ID" -H 'Content-Type: application/json' -d '{"fullName":"Bazarali Daulet","email":"daulet.updated@example.com","groupCode":"IT2-2308","studentNumber":"37392"}' && echo
printf '\nDELETE student ID %s (expect 204): ' "$ID"
CODE="$(curl --silent -o /dev/null -w '%{http_code}' -X DELETE "$BASE/api/students/$ID")"
echo "$CODE"; test "$CODE" = 204
printf '\nNONEXISTENT student (expect 404): '
CODE="$(curl --silent -o /dev/null -w '%{http_code}' "$BASE/api/students/999999")"
echo "$CODE"; test "$CODE" = 404
printf '\nCRUD SMOKE TEST: SUCCESS\n'
