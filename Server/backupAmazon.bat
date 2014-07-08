mongodump --host ec2-54-193-44-87.us-west-1.compute.amazonaws.com --port 27017 --username omo --password yomama1963! --out d:\backups\mongodump-2012-10-24 --db omo
rem cd target
rem scp -i  D:\Projects\EC2\amazonLinux.pem omo.war ec2-user@ec2-54-193-44-87.us-west-1.compute.amazonaws.com:/env/tomcat/apache-tomcat-7.0.52/webapps
 pause