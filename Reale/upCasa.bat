@echo off
echo "mi sposto"
cd RealeV3\it.unibo.eclipse.qak.robotMinds19\

echo "pulisco"
del "build\distributions\it.unibo.eclipse.qak.robotMinds19-1.0.zip"
del /s /q "build\distributions\qakV3"

echo "buld zip"
call gradle -b build_ctxRobotMind.gradle distZip 

echo "estrazione..."
powershell Expand-Archive -Force "build\distributions\it.unibo.eclipse.qak.robotMinds19-1.0.zip" -DestinationPath "\\11.0.0.18\AnotherRobot\qak\qakV3"
echo "copio .pl"
copy "*.pl" "\\11.0.0.18\AnotherRobot\qak\qakV3\it.unibo.eclipse.qak.robotMinds19-1.0\bin"

pause