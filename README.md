1-> Here under this project we have created simple Rest endpoints which will
be able to do all CRUD Operations.

2-> we have also created the JUnit of all layers. 
    a> Please refer below link for Controller Testing Concepts -- https://spring.io/guides/gs/testing-web/    
    b> Another method to test the controller -- https://howtodoinjava.com/spring-boot2/testing/rest-controller-unit-test-example/

3-> In this code we are also using spotless plugin. to add spotless plugin follow below steps -

plugins{
id 'com.diffplug.spotless' version '6.16.0'
}

spotless {
java {
googleJavaFormat()
importOrder 'com', 'io', 'java', 'javax', 'org'
removeUnusedImports()
}
}

compileJava.dependsOn 'spotlessApply'

4-> for logging purpose we are using apache.log4j -
implementation 'org.apache.logging.log4j:log4j-api:2.20.0'
implementation 'org.apache.logging.log4j:log4j-core:2.20.0'

5-> Here we are using swagger UI, so to add swagger UI functionality follow below steps -

add below dependency in build.gradle
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'

To access SwaggerUI use below URL
http://localhost:8080/swagger-ui/index.html

6-> @SpringBootApplication -- https://learnjava.co.in/the-springbootapplication-annotation-explained/

7-> For Dependency Injection -- https://reflectoring.io/constructor-injection/#:~:text=for%20that%20matter
