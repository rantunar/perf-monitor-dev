# Performance report generation in html using spring AOP and custom annotation

#### step1
Run the application which consume one file upload endpoint and hit the endpoint with postman.
http://localhost:8080/v1/file/upload

#### step2
After successful file upload stop the application then in the project directory a html will be generated as "perf.html" where all the method performance can be shown.

#### step3
Using spring AOP and custom annotation @TrackExecutionTime the performance of each method having tagged the annotation will be logged in html.

#### step4
Defult SLA is 5ms that means if any method takes more than 5ms to execute then the html data will be red for that method, SLA can be changed in TrackExecutionTimeAdvice.java file.


