Framework:
----------
This PetStore test automation framework is built using Rest Assured, TestNG library. Also, LOG4J framework is used to log the details/information. Logs are tracked in test.log files generated in target/log directory. Extent Report is used to generate the HTML report and is overwritten post every execution under target/ExtentReport directory. 

PetStore automation framework is divided into 2 parts:
1.  Base Framework:
    ---------------
        Base Framework handles following tasks:
            a. Interaction with service and verification of response. There are 2 services / apis as of now in the Base Framework which covered in following packages:
                i.  pet -
                    This package represents the pet api and, has 2 packages:
                        a.  handler -
                            This package has class for the service / endpoint which will have method to interact with service and provide verification of the service response.
                        b.  model -
                            This package has all the POJO cloases for the api response. 
                ii. store -
                    This package represents the pet api and, has 2 packages:
                        a.  handler -
                            This package has class for the service / endpoint which will have method to interact with service and provide verification of the service response.
                        b.  model -
                            This package has all the POJO cloases for the api response.
                            
            b.  Generate HTML Report [Extent Report]. This is covered in extent_report package with the help of TestNG Listner.
            
            c.  Logging details / information in Report. LOG4J and RestAssured Logger are used to implement this, and covered in APILogger and RestAssuredLogger class of util Package.
            
            d.  Read the system properties and api configurations. This is covered in MicroserviceEnvConfig class and WebServiceConfig interface of util package
            
            e.  Generate Random data. This is covered in RandomDataGenerator class of util package.
            
2.  Test / Test Cases / Test Scripts:
    ---------------------------------
            Test section has test cases / scripts organized in respective api packages. 

Run/Execute:
------------
Command to execute test:-
    mvn test [Optional: baseURL] [Optional: test.names]
 
Optional Parameter: baseURL - 
    This is optional parameter to provide application website name
    Default value is: http://petstore.swagger.io/

Optional Parameter: test.names - 
    This is optional parameter.
    Default value is: regression
    Possible Values are:
        petAPI [consists of Test1, Test2]
        storeAPI [consists of Test3]
        regression [consists of Test1, Test2, Test3]
        
e.g.: 
1. Command to execute all 3 test:
    mvn test -DbaseURL=http://petstore.swagger.io/ -Dtest.names=regression
2. Command to execute only Test1 and Test2:
    mvn test -DbaseURL=http://petstore.swagger.io/ -Dtest.names=petAPI
3. Command to execute only Test 3:
    mvn test -DbaseURL=http://petstore.swagger.io/ -Dtest.names=storeAPI
4. Command to execute all 3 test without providing base url:
    mvn test -Dtest.names=regression
    
Report:
-------
    Report is generated in target/ExtentReport directory. This report is overwritten every time after execution.

Logs:
-----
    Logs are generated in target/log directory which are overwritten just like report every time after execution.