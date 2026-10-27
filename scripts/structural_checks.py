from pathlib import Path
p = Path(__file__).resolve().parent.parent
required = [
    'Dockerfile', 'Jenkinsfile', 'docker-compose.yml', 'build.gradle',
    'settings.gradle', 'gradlew', 'README.md', '.env.example', '.gitignore',
    'scripts/Bazarali_Daulet_system_report.sh',
    'src/main/java/kz/iitu/bazaralidaulet/studentapi/StudentController.java',
    'src/main/java/kz/iitu/bazaralidaulet/studentapi/StudentRepository.java',
    'src/test/java/kz/iitu/bazaralidaulet/studentapi/StudentApiTests.java',
]
missing = [f for f in required if not (p / f).is_file()]
print('STRUCTURAL CHECK:', 'PASS' if not missing else 'FAIL')
for f in missing: print('MISSING:', f)
print('NOTE: This does not replace Gradle, Docker, API, database, Git, or Jenkins checks.')
raise SystemExit(bool(missing))
