systemhealthweb
======================
Author: Phat Tran  
Level: Intermediate  
Technologies: Quartz job scheduling and websockets in the backend, html5/jquery web client  
Target Product: WildFly  


What is this?
-------------




Building
--------
The compiling/building/deploy uses maven3.

To build, install and deploy: (make sure wildfly 9.x is up and running first)
mvn clean install wildfly:deploy

To stop/undeploy:
mvn wildfly:undeploy


