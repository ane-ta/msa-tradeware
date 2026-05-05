call ./../export-org.bat ./docs/adr.org ./results/
call ./../export-org.bat ./docs/implementation-plan.org ./results/
xcopy /y ".\docs\C2.puml" ".\results\"