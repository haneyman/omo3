mvn clean
mvn package -P "Skip Tests"
cd target
copy omo.war D:\tomcat\webapps
pause