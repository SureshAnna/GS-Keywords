set CURRENTPATH=%~dp0
set CLASSPATH=target\classes;C:\Users\aotusvc\.m2\repository\com\trinet\keywordFramework\0.0.1-SNAPSHOT\keywordFramework-0.0.1-SNAPSHOT.jar;%CLASSPATH%;
cd %CURRENTPATH%
mvn  test && java -DmailList=%1 -DtestSiteURL=%2 -DtestBrowser=%3 -Denv=%4 -DDB_USER=%5 -DDB_PWD=%6 -Dtesttype=regression org.testng.TestNG %CURRENTPATH%testng.xml


