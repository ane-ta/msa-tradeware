-- Этот фильтр принудительно заменяет текст \<br\> на реальный перенос
function Plain(el)
  return pandoc.walk_inline(el, {
    Str = function(s)
      if s.text:find("<br") then
        return pandoc.RawInline("html", "<br>")
      end
    end
  })
end

-- То же самое для ячеек, если они в блоках
function Para(el)
  return Plain(el)
end
