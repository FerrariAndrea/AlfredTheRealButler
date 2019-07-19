%%-----------------conoscenza
robotPosX(0).
robotPosY(0).
robotOrientation(0).

%%----------------ottenere lo stato attuale
convertIntToOrientation(O):-robotOrientation(X), X = 0, O = "sud",!.
convertIntToOrientation(O):-robotOrientation(X), X = 1, O = "est",!.
convertIntToOrientation(O):-robotOrientation(X), X = 2, O = "nord",!.
convertIntToOrientation(O):-robotOrientation(X), X = 3, O = "ovest",!.
actualRobotPos(X,Y,O):-robotPosX(X),robotPosY(Y),convertIntToOrientation(O).

%%----------------modificare lo stato
updatePosX(X):-retract(robotPosX(_)), assert(robotPosX(X)).
updatePosY(Y):-retract(robotPosY(_)), assert(robotPosY(Y)).
updateOrientation(O):-retract(robotOrientation(_)), assert(robotOrientation(O)).

%%----------------resorver automatico dato una mossa riuscita
