# Git: персональные ветки, конфликт, тег

Проект: `bazarali-daulet-student-api`. Используй новый репозиторий GitHub с таким именем; старый `bazarali-daulet-devops` не меняем.

## Инициализация
```bash
git init -b main
git config user.name "Daulet Bazarali"
git config user.email "YOUR_GITHUB_EMAIL"
bash scripts/make_official_gradle_wrapper.sh
git add .
git commit -m "Initial commit: Bazarali Daulet Student API"
```
Убедись, что `.env` и `logs/*.log` игнорируются: `git check-ignore .env`.

## Персональная CRUD-ветка
```bash
git switch -c feature/bazarali-daulet-crud
```
Измени одну CRUD-операцию в `StudentController.java` — например, добавь нормализацию регистра email при создании и обновлении; затем обнови тест для проверяемого поведения.
```bash
git add src/main/java src/test/java
git commit -m "Bazarali Daulet: improve CRUD input normalization"
```
Добавь README с описанием изменений и выполни второй осмысленный коммит:
```bash
git add README.md
git commit -m "Bazarali Daulet: document CRUD behavior"
git switch main
git merge --no-ff feature/bazarali-daulet-crud
```

## Управляемый merge conflict
Для воспроизводимого конфликта сначала добавь одну строку в `README.md` в `main`, сохрани её коммитом, затем создай feature-ветку и по-разному измени ту же строку в каждой ветке:
```bash
# На main, ПЕРЕД созданием documentation-ветки:
printf '\nDocumentation status: DRAFT\n' >> README.md
git add README.md && git commit -m "Bazarali Daulet: documentation baseline"
git switch -c feature/bazarali-daulet-documentation
sed -i.bak 's/Documentation status: DRAFT/Documentation status: FEATURE/' README.md
rm -f README.md.bak
git add README.md && git commit -m "Bazarali Daulet: documentation feature"
git switch main
sed -i.bak 's/Documentation status: DRAFT/Documentation status: MAIN/' README.md
rm -f README.md.bak
git add README.md && git commit -m "Bazarali Daulet: documentation on main"
git merge feature/bazarali-daulet-documentation
```
Ожидается CONFLICT. Открой README, убери `<<<<<<<`, `=======`, `>>>>>>>`, оставь осмысленную итоговую строку. Потом:
```bash
git add README.md
git commit -m "Bazarali Daulet: resolve README merge conflict"
git log --oneline --graph --all -n 15
```

## GitHub и тег
```bash
git remote add origin https://github.com/daulet699/bazarali-daulet-student-api.git
git tag v1.0-bazarali-daulet
git push -u origin main
git push origin feature/bazarali-daulet-crud feature/bazarali-daulet-documentation
git push origin v1.0-bazarali-daulet
git tag -l
git remote -v
```
**Никогда не передавай в чат GitHub токен.** Если по какой-то причине GitHub создаст репозиторий как private, Jenkins нужны credentials.
