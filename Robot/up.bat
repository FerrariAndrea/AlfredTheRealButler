@echo off
set /P var=< ip.txt
set progectName=it.unibo.realrobot
set versione=3
echo mi sposto in RealeV3\%progectName%\ 
cd RealeV3\%progectName%\

echo pulisco
del build\distributions\%progectName%-1.0

echo faccio la build
echo buld zip
call gradle -b build_ctxRobotMind.gradle distZip 

echo estrazione build\distributions\%progectName%-1.0.zip...
powershell Expand-Archive -Force "build\distributions\%progectName%-1.0.zip" -DestinationPath "\\%var%\AnotherRobot\qak"
echo copio .pl
copy "*.pl" "\\%var%\AnotherRobot\qak\%progectName%-1.0\bin"

pause