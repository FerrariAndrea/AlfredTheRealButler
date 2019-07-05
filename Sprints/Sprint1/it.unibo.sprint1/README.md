# Sprint 1
In this sprint we started from sprint0 which was Requirements Analysis and we arrived to the conclusion that we needed 3 different contexts in our system.
  - CTX MAITRE
  - CTX RESOURCE MODEL
  - CTX ROBOT

***CTX Maitre*** contains temporarly only a message that gets forwarded to ***CTX RESOURCE MODEL*** which is a *moveForward* message.
***CTX ResourceModel*** contains our KB and also the resourceModel of the robot as explained in our LAB. Communicates with the mind of our Robot.
***CTX Robot*** is the context containing basicrobot and robotmind. This will be deployed on Raspberry. For performance reason, probably we need to move ResourceModel (very late sprints), in CTX Robot.