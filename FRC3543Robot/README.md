# Robot 2018

## Path Recording and Playback (PRAP)

We came up with PRAP as an easy way to train the robot on complex paths, as a solution to the autonomous mode and an alternative to working with more complicated path planning algorithms and techniques.  We subclassed the standard RobotDrive class so that whenever the left and right motor speed was written we could capture and log it.  Using this, we can record the values fed into the robot using the joystick.

Here's how it works:

1. Use the right joystick trigger to put the robot in arcade drive mode
2. Hold down the 6 button on the left joystick.  This puts the drive into "record" mode.  It will record as long as you are holding the button.
3. Drive carefully and reproduce the path you want.  Drive slowly and carefully as jerky motions don't reproduce as reliably.
4. When you reach your destination, release the joystick button.  In the log output you will see the path written (select and copy it to the clipboard).  It will also get stored in the OI.
5. Reset the robot to the starting position, and select "RECORDED PATH" in the "Recorded paths" widget
6. Test the path you recorded by pressing the 7 button. The robot will replay the same path you just drove.

Storing the path:

In the RecordedPaths class, add an entry to the RecordedPaths class containing the path you copied to the clipboard in step 4 above.  Change the name at the start of the string to something descriptive.  Build and redeploy the robot code.  The new path should be available in the recorded paths pick list in the operator interface, so you can re-use it anytime.
