<%*
let selection = tp.file.selection();
// Регулярное выражение ищет "> [!ЛЮБОЕ_СЛОВО] " в начале текста
const calloutRegex = /^> \[!\w+\] ?/;

if (selection) {
    // ПРОВЕРКА: Если выделение начинается как коллаут
    if (calloutRegex.test(selection)) {
        // УДАЛЯЕМ ЛЮБОЙ КОЛЛАУТ
        let unwrapped = selection
            .split('\n')
            .map((line, index) => {
                if (index === 0) {
                    // Удаляем заголовок коллаута: "> [!ТИП] "
                    return line.replace(calloutRegex, "");
                }
                // Удаляем символы цитирования "> " из последующих строк
                return line.replace(/^> ?/, "");
            })
            .join('\n');
        
        // Очищаем от пустых строк в начале, если они остались после удаления заголовка
        tp.file.cursor_append(unwrapped.trimStart());
    } else {
        // ДОБАВЛЯЕМ (по умолчанию ADR, если хотим обернуть чистый текст)
        let wrapped = selection
            .split('\n')
            .map((line, index) => index === 0 ? `> [!ADR] ${line}` : `> ${line}`)
            .join('\n');
        tp.file.cursor_append(wrapped);
    }
} else {
    // Если ничего не выделено - вставляем пустую заготовку
    tp.file.cursor_append("> [!ADR] \n> ");
}
%>
