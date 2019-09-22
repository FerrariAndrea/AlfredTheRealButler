# Alfred - The Real Butler

This University Project was made for the UNIBO course "Ingegneria dei Sistemi Software 2019" kept by Prof. Antonio Natali (https://www.unibo.it/it/didattica/insegnamenti/insegnamento/2018/385373).


## Description
The goal of this project is available in HTML Format at `userDocs/Objective/TemaFinaleBo19.html`.

- Our Robot uses an AI Planner to dinamically calculate a sequence of moves for reaching a position. 
- Our Robot also uses Prolog as a Knowledge Base .
- Everything is mainly developed using the DSL QAK Language (external DSL for generating Kotlin code) developed by Antonio Natali.
- The Robot is equipped with a Raspberry which was properly configured for this purpose.
- MQTT and CoAp are used in this project as described in the objective of this project.
- The Virtual Environment that was used to test the robot *in a virtual environment* was developed by *Pierfrancesco Soffritti* (https://pierfrancescosoffritti.com/), currently working for Google.
- Frontend is developed entirely in NodeJS
- SCRUM was used as method for organizing our work. All our Sprints are available to be consulted. We reached the final stage of the project in a *incremental way*.


## Virtual Environment Instructions

To launch correctly our virtual system:

1. Clonare la repository (it might require a while for some demos we pushed)

2. Aprire come progetto in Eclipse lo Sprint6 (/Sprints/Sprint6/it.unibo.sprint6)

3. Eseguire un `gradle build eclipse` per sistemare le dipendenze nella cartella sprint6/it.unibo.sprint6 (è disponibile un .bat      Sprint6/it.unibo.sprint6/gradleBuildEclipse.bat)

4. Installare con `npm install` il virtual environment (è disponibile un .bat /Sprints/it.unibo.robots19/node/WEnv/install.bat)

5. Installare `npm install` il frontend (è disponibile un .bat /Sprints/Sprint6/installFrontend.bat)

6. Lanciare l'ambiente virtuale ed il frontend (è disponibile un .bat /Sprint6/launchFrontend&WEnv.bat)

7. Lanciare da eclipse i seguenti contesti: **ctxRobot**, **ctxResourceModel**, **ctxExplorer**, **ctxFridge**, **ctxPantry**, **ctxDishwasher**.


## Demos
Many demos (go check it out!!) are available at Demos


## The Team (sorted by Last Name)


**Cesarano Luca** 
https://lucacesarano.com
https://github.com/Cesarsk

**Croce Andrea**
https://github.com/Shin94

**Ferrari Andrea**
https://github.com/FerrariAndrea


## Tags
*PROLOG, IA, AI, ARTIFICIAL INTELLIGENCE, RASPBERRY, DSL, QAK, GOOGLE, VIRTUAL ENVIRONMENT, NODEJS, JAVASCRIPT, MQTT, COAP, IOT, SMART DEVICES*


## Copyright
Check LICENSE.MD

