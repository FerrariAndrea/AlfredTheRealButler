cd it.unibo.robots19
cd node
cd WEnv

SET /P answer=Do you need install dependentes?[Y/N]
if /I "%answer%" EQU "N" goto :noDep

cd server
call npm install
cd ..
cd WebGLScene
call npm install
cd ..
:noDep
cd server
cd src
node main 8999