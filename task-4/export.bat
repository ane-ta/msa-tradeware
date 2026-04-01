call ./../export-org.bat ./docs/adr-architecture.org ./results/
call ./../export-org.bat ./docs/adr-worker.org ./results/
xcopy /y ".\docs\C2.puml" ".\results\"