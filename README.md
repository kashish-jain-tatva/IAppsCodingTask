##building and running the application with maven and java

[Pre-requisites]: java 17 should be installed.
1. command to install the application: "./mvnw clean install"
2. go to target folder and then open cmd and run command "java -jar IAppsCodingTask-0.0.1-SNAPSHOT.jar"

##using Docker to run the application
1. command to install the application: "./mvnw clean install"
2. Run command "docker build -t IAppsCodingTask:1.0 ." in the root directory of project.
3. Run command "docker-compose up" in the same directory and wait for the application to start up.


##API curl Documentation
1) curl request to upload the XML file:
curl --location 'http://localhost:8080/api/epaper/upload' \
--form 'file=@"${XMLFileLocation}"'
Note: replace this `${XMLFileLocation}` with path of your XML File.

Here for all of the below requests,all the request parameters have default values i.e pageSize has 10 as default value, pageNumber has 0 as default value, sort has uploadTime as default value and descending has false as default value:

2) curl request to get all the record with pagination and sorting: 
curl --location 'http://localhost:8080/api/epaper/getAll?pageSize=10&pageNumber=0&sort=uploadTime&descending=true'

3) curl request to get all records by width
curl --location 'http://localhost:8080/api/epaper/getByWidth/1280?pageSize=10&pageNumber=0&sort=uploadTime&descending=true'

4) curl request to get all records by height
curl --location 'http://localhost:8080/api/epaper/getByHeight/752?pageSize=10&pageNumber=0&sort=uploadTime&descending=true'

5) curl request to get all records by dpi
curl --location 'http://localhost:8080/api/epaper/getByDpi/160?pageSize=10&pageNumber=0&sort=uploadTime&descending=true'

6) curl request to get all records by newspaper name
curl --location 'http://localhost:8080/api/epaper/getByNewspaperName/a?pageSize=10&pageNumber=0&sort=uploadTime&descending=true'