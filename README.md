# Bazarali Daulet — Student API

**Student:** Daulet Bazarali • **Group:** IT2-2308 • **ID:** 37392

Individual DevOps midterm Spring Boot REST CRUD + PostgreSQL + Gradle + Docker Compose + Jenkins.

## Requirements
- Docker Desktop started; Git; Java 21 (for Jenkins host Gradle), curl; Internet for first dependency download.
- Default API: `http://localhost:8092`; health: `/actuator/health`.
- `POST /api/students`, `GET /api/students`, `GET /api/students/{id}`, `PUT /api/students/{id}`, `DELETE /api/students/{id}`. Missing ID yields 404; invalid data 400.

## Quick start
```bash
cp .env.example .env
# Edit .env and replace the default password with your own local value.
docker compose up -d --build
docker compose ps
curl http://localhost:8092/actuator/health
```

## CRUD examples
```bash
curl -i -X POST http://localhost:8092/api/students -H 'Content-Type: application/json' -d '{"fullName":"Daulet Bazarali","email":"daulet@example.com","groupCode":"IT2-2308","studentNumber":"37392"}'
curl -s http://localhost:8092/api/students
curl -s http://localhost:8092/api/students/1
curl -i -X PUT http://localhost:8092/api/students/1 -H 'Content-Type: application/json' -d '{"fullName":"Bazarali Daulet","email":"daulet.updated@example.com","groupCode":"IT2-2308","studentNumber":"37392"}'
curl -i -X DELETE http://localhost:8092/api/students/1
curl -i http://localhost:8092/api/students/999999
```
The ID is assigned by PostgreSQL; use the actual ID from your POST response instead of assuming `1`. For a single automated run after startup: `bash scripts/smoke_crud.sh`.

## Verify persistence
Create a record, `docker compose restart`, then retrieve it again. A named volume `postgres_data` preserves PostgreSQL records. **Do not run `docker compose down -v`** unless deliberately deleting data.

## Local Gradle tests
```bash
./gradlew test
```
`gradlew` initially bootstraps Gradle 8.14.3 if an official wrapper JAR has not yet been generated. Run `bash scripts/make_official_gradle_wrapper.sh` once, then commit generated Gradle wrapper scripts and JAR.

## Detailed setup / evidence
See `docs/RUN_ON_MAC_RU.md`, `docs/GIT_MIDTERM_RU.md`, `docs/JENKINS_RU.md`, `docs/TEAMS_CHECKLIST_RU.md`.

Do not commit `.env`, credentials or other private data. The PostgreSQL password here is only for a local lab environment, not production.

## Student API and CRUD Documentation — Daulet Bazarali
