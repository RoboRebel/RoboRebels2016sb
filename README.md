# RoboRebels2016sb
StL Priory RoboRebels 2016 robotics control code built on the Strongback libraries

To get this project to compile you will need to have references to the wpilib, networktables, and Strongback libraries (see https://github.com/strongback).

If using the Eclipse IDE follow the setup instructions in the 'Using Strongback' GitBook (https://www.gitbook.com/book/strongback/using-strongback/details) for creating a new Eclipse project that includes references to the above mentioned libraries.  The resulting .classpath for your Eclipse project should look something like below.

<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="src" path="src"/>
    <classpathentry kind="src" output="build/test-classes" path="testsrc"/>
    <classpathentry kind="var" path="wpilib" sourcepath="wpilib.sources"/>
    <classpathentry kind="var" path="networktables" sourcepath="networktables.sources"/>
    <classpathentry kind="con" path="org.eclipse.jdt.USER_LIBRARY/Strongback"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
	<classpathentry kind="output" path="build/classes"/>
</classpath>

