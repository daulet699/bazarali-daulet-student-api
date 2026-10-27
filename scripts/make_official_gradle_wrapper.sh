#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
./gradlew wrapper --gradle-version 8.14.3 --distribution-type bin
./gradlew wrapper --gradle-version 8.14.3 --distribution-type bin
chmod +x ./gradlew
ls -l gradlew gradle/wrapper/
