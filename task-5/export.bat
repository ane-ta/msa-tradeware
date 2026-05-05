call ./../export-org.bat ./docs/adr.org ./results/
call ./../export-org.bat ./docs/adr-metrics.org ./results/
call ./../export-org.bat ./docs/adr-alerts.org ./results/
xcopy /y ".\docs\С2-to-be.puml" ".\results\"