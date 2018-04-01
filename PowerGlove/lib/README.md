# Libraries folder

The following libraries are not included in the repo, but must be copied into this folder in order for the powerglove app to run on the PC and read the Ardunio serial data and write to the robot networktables.  Download them and install in this folder, then make sure the RXTXComm.jar is on the build path, and that the lib/ folder is added as a native library location.

From: https://wpilib.screenstepslive.com/s/currentCS/m/75361/l/851714-creating-a-client-side-program (links at bottom of page):

ntcore-java-4.0.0.jar
ntcore-jni-4.0.0-all.jar
wpiutil-java-3.0.0.jar

From: RXTX Serial drivers for 64-bit windows 10 from http://mfizz.com/oss/rxtx-for-java :

RXTXcomm.jar
rxtxParallel.dll
rxtxSerial.dll

