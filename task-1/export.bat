@echo off
echo 🚀 Сборка документации из Org в Markdown...

:: Команда экспорта (одной строкой)
pandoc adr.org -f org -t gfm -o results/adr.md --wrap=none

echo ✅ Готово! Файлы обновлены.
pause