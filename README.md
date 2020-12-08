JCBC Test App
=============

A very simple app to test a Microsoft SQL Server JDBC connection.

To run execute:

>run.bat

and enter the JDBC Url, username and password to test.

Example JDBC URLS are:

* jdbc:sqlserver://localhost:1433;database=<database_name>;sendStringParametersAsUnicode=false

To (re)build execute:

>ant

This program includes and uses by default the SQL JDBC 8.2.0 JRE 8 java library in
the lib directory. For testing Java runtimes other
than Java 8 you should replace the JAR in here.