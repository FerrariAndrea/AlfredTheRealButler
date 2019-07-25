@echo off
echo "mi sposto"
cd RealeV3\it.unibo.eclipse.qak.robotMinds19\
echo "buld zip"
call gradle -b build_ctxRobotMind.gradle distZip 

echo "estrazione..."
powershell Expand-Archive -Force "build\distributions\it.unibo.eclipse.qak.robotMinds19-1.0.zip" -DestinationPath "build\distributions\qakV3"
echo "copio sul robot fisico..."
ROBOCOPY "build\distributions\qakV3\it.unibo.eclipse.qak.robotMinds19-1.0\bin" "\\192.168.43.15\AnotherRobot\qak\qakV3\bin" /E /IS /MOVE
ROBOCOPY "build\distributions\qakV3\it.unibo.eclipse.qak.robotMinds19-1.0\lib" "\\192.168.43.15\AnotherRobot\qak\qakV3\lib" /E /IS /MOVE
echo "estratto :)"
pause
