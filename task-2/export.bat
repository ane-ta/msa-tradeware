call ./../export-org.bat adr.org ./results/
call ./../export-org.bat implementation-plan.org ./results/
xcopy /y ".\solution\C2.puml" ".\results\"