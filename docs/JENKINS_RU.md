# Jenkins CI/CD — midterm

Создай новый Pipeline Job `Bazarali_Daulet_Student_API`:
- Definition: `Pipeline script from SCM`; SCM: Git;
- Repository: `https://github.com/daulet699/bazarali-daulet-student-api.git`;
- Branch: `*/main`; Script Path: `Jenkinsfile`.
- Java 21 и Docker Desktop должны быть доступны Jenkins; Jenkinsfile содержит путь к Docker для прошлого Mac.
- Для публичного GitHub Credentials не нужны; для приватного храни их в Jenkins Credentials, не в Jenkinsfile.

После публикации запусти Build Now, убедись, что стадии Checkout, Build (Gradle), Test, Docker Build, Verify = SUCCESS.

## Триггер
В Jenkinsfile включён `pollSCM('H/5 * * * *')`. После первого успешного запуска сделай новый коммит и push в main. Через несколько минут Jenkins должен сам заметить изменение. Демонстрируй новый Build History (причина запуска: SCM change), Console Output. `H/5` использует распределённый момент опроса — не ровно каждые 5 минут от команды.

## Controlled failure (только тестовая ветка)
1. `git switch -c feature/bazarali-daulet-ci-failure`.
2. Создай `src/test/java/kz/iitu/bazaralidaulet/studentapi/ControlledFailureTest.java` с кодом:
```java
package kz.iitu.bazaralidaulet.studentapi;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
class ControlledFailureTest { @Test void demonstrateFailure() { assertTrue(false, "planned test failure"); } }
```
3. Commit/push test branch. Создай *второй* Jenkins Pipeline Job `Bazarali_Daulet_Student_API_Test` со Branch Specifier `*/feature/bazarali-daulet-ci-failure`.
4. Сборка должна завершиться `FAILURE` на Test. Сохрани скриншот.
5. На той же тестовой ветке замени `assertTrue(false` на `assertTrue(true`, сохрани, commit/push и Build Now. Сборка должна стать `SUCCESS`. Сохрани скриншот и объясни исправление.
6. Основной main / Jenkins Job не трогай. Если хочешь, отдельную тестовую ветку можно оставить как доказательство эксперимента.

### Про различие CI/CD
CI собирает и тестирует изменения автоматически; Continuous Delivery поддерживает готовый к выпуску артефакт, а Continuous Deployment размещает его в работающей среде без ручного подтверждения. В этом Midterm автоматический deployment **не обязателен**.
