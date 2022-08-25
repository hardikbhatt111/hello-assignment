set projectLocation=C:\Users\hardikb\eclipse-workspace\hello-assignment

cd %projectLocation%

set classpath=%projectLocation%\bin;%projectLocation%\target\test-classes\demo\site;%projectLocation%\lib\*

java -cp %projectLocation%\target\test-classes\demo\site;%projectLocation%\lib\* org.testng.TestNG %projectLocation%\testng.xml

pause