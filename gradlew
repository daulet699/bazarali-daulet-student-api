#!/usr/bin/env bash
set -euo pipefail
BASE="$(cd "$(dirname "$0")" && pwd)"
if [[ -f "$BASE/gradle/wrapper/gradle-wrapper.jar" ]]; then
  exec java -Dorg.gradle.appname=gradlew -classpath "$BASE/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
fi
VERSION=8.14.3
INSTALL="${GRADLE_USER_HOME:-$HOME/.gradle}/bootstrapped-gradle/gradle-$VERSION"
if [[ ! -x "$INSTALL/bin/gradle" ]]; then
  CACHE="${GRADLE_USER_HOME:-$HOME/.gradle}/bootstrapped-gradle"
  mkdir -p "$CACHE"
  ZIP="$CACHE/gradle-$VERSION-bin.zip"
  echo "Downloading Gradle $VERSION (first run only)..."
  curl --fail --location --retry 3 --connect-timeout 20 --output "$ZIP" "https://services.gradle.org/distributions/gradle-$VERSION-bin.zip"
  unzip -q -o "$ZIP" -d "$CACHE"
  rm -f "$ZIP"
fi
exec "$INSTALL/bin/gradle" -p "$BASE" "$@"
