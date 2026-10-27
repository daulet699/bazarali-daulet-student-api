# Midterm: проверка на MacBook по шагам

**Не запускай `docker compose down -v`**: команда удалит постоянный том с данными PostgreSQL.

## Первый запуск
1. Скачай и распакуй ZIP. Открой Terminal, перейди в распакованную папку.
2. Убедись, что Docker Desktop работает: `docker version`.
3. `cp .env.example .env` и замени `CHANGE_ME_TO_A_LONG_LOCAL_PASSWORD` своим локальным паролем. Не показывай пароль на скриншотах.
4. `docker compose config --quiet`.
5. `docker compose up -d --build` (первый запуск скачивает Gradle, Python не используется; сборка может требовать свободную память Docker Desktop).
6. `docker compose ps` — у `db` статус healthy, приложение запущено, через некоторое время должно стать healthy.
7. `curl -i http://localhost:8092/actuator/health` — статус HTTP 200 и `UP`.

## CRUD и сохранение PostgreSQL
В `README.md` есть 6 curl-команд. Обрати внимание на ID из ответа POST (он может быть не 1).
После POST: `docker compose restart` и `curl http://localhost:8092/api/students` — запись должна сохраниться. Только после демонстрации сохранения выполняй DELETE.

## Linux
Для настоящего Linux можно использовать Ubuntu-контейнер:
```bash
docker run --rm -it -v "$PWD:/work" -w /work ubuntu:24.04 bash
```
Внутри Linux:
```bash
apt-get update && apt-get install -y procps
chmod 744 scripts/Bazarali_Daulet_system_report.sh
./scripts/Bazarali_Daulet_system_report.sh
ls -l scripts/Bazarali_Daulet_system_report.sh
tail -n 5 logs/Bazarali_Daulet_system_report.log
grep -i Bazarali logs/Bazarali_Daulet_system_report.log > Bazarali_Daulet_log_analysis.txt
cp README.md backups/Bazarali_Daulet_README_backup.md
cmp README.md backups/Bazarali_Daulet_README_backup.md
ps aux | head
```
`backups/` и `logs/` монтируются с хоста и останутся после выхода из контейнера. **На macOS после `chmod` в контейнере** те же права могут отображаться иначе, проверь внутри Linux.

## Сохранение доказательств
Скриншоты: Linux дерево файлов и script log; 744; backup; анализ; Git коммиты/конфликт/тег; два Docker сервиса; curl CREATE/READ/UPDATE/DELETE/404; `docker volume ls`; Jenkins SUCCESS/FAILURE/автозапуск.

**Не загружай ZIP в Teams как доказательство полного выполнения до реального запуска и коммитов.** В Teams должны быть правдивые результаты.
