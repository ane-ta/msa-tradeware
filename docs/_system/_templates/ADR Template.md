<%*
// 1. Автоматический расчет ID (ADR-XXX)
const folder = "ADR"; // Укажите вашу папку с решениями
const files = app.vault.getMarkdownFiles().filter(f => f.path.startsWith(folder));
let nextNum = 1;
if (files.length > 0) {
    const nums = files.map(f => parseInt(f.name.match(/\d+/)) || 0);
    nextNum = Math.max(...nums) + 1;
}
const adrID = "ADR-" + nextNum.toString().padStart(3, '0');
const title = tp.file.title.replace(/Untitled|Без названия/g, "Название решения");

// 2. Переименование файла при создании
await tp.file.rename(`${adrID} ${title}`);
%>---
id: <% adrID %>
date: <% tp.date.now("YYYY-MM-DD") %>
status: proposed
deciders: [Я]
---
# <% adrID %>: <% title %> ^<% adrID.toLowerCase() %>

## Контекст
<!-- Какую проблему мы решаем? Какие ограничения (бизнесовые или технические) есть? -->

## Проблема

## Условия

## Рассмотренные варианты
1. **Вариант А**: [Название]
2. **Вариант Б**: [Название]

## Решение
Мы выбрали **<% title %>**, потому что...

### Аргументация (Плюсы и Минусы)
*   **Плюсы**: 
    *   ...
*   **Минусы**: 
    *   ...

## Последствия
<!-- Что изменится в системе? Понадобится ли обучение команды? Какие риски? -->

## Связи
<!-- Ссылки на другие ADR, например: Вытесняет [[ADR-001]] -->
