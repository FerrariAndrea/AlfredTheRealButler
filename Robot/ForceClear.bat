@echo off
set /P var=< forceclearProject.txt

echo avvio STERMINIO del progetto %var%

echo mi sposto...
cd %var%

echo PIALLO la cartella bin
@RD /S /Q bin
echo PIALLO la cartella build
@RD /S /Q build
echo PIALLO la cartella src-gen
@RD /S /Q src-gen
echo esegui la build
call gradle build eclipse
pause