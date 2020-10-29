echo 'PIPELINE AUTOMATION TEST'

echo 'Jmeter Test'
cd load
jmeter -n -t LoadTesting.jmx
cd ../

echo 'Newman Test'
node --version
cd api/newman
newman run APITesting.postman_collection.json 
cd ../../

echo 'RestAssured Test'
cd api/restassured/APITesting
mvn clean test
cd ../../../

