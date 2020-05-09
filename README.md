# Spring Microservices 

This is my sample of creating an app based on microservices architecture in Spring. Here are my repos

1. ConfigServer: https://github.com/Sonnpm197/Spring_ConfigServer
2. ZuulGateway: https://github.com/Sonnpm197/Spring_Zuul
3. OAuth2Server: https://github.com/Sonnpm197/Spring_OAuth2
4. SampleService: https://github.com/Sonnpm197/Spring_SampleService
5. QuizletService: https://github.com/Sonnpm197/Spring_QuizletService
6. ConfigurationRepo: https://github.com/Sonnpm197/LearningEnglishWebConfiguration
7. EurekaServer: https://github.com/Sonnpm197/Spring_EurekaServer
8. JWT only (not with OAuth2): https://github.com/Sonnpm197/Spring_JwtSwaggerAuditing
9. ZippkinServer: https://github.com/Sonnpm197/Spring_ZipkinServer
10. SampleSleuth: https://github.com/Sonnpm197/Spring_Sleuth

##Installations

1. IntelliJ 2019
2. JDK8 + Maven + Kafka/Zookepper tutorial: https://dzone.com/articles/kafka-with-spring-cloud-stream
3. Set env variable: ENCRYPT_KEY = IMSYMMETRIC (for decrypt properties from ConfigServer in each service)
4. Setting up papertrail for distributed tracing: https://dzone.com/articles/getting-spring-boot-work

**Kafka & Zookeeper**

https://www.cloudkarafka.com/blog/2018-07-04-cloudkarafka_what_is_zookeeper.html

**Download (Do not download Zookeper/Kafka src files)**

Zookeeper: https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.6.1/apache-zookeeper-3.6.1-bin.tar.gz

Kafka: https://www.apache.org/dyn/closer.cgi?path=/kafka/2.5.0/kafka_2.12-2.5.0.tgz

Redis for Window: https://github.com/microsoftarchive/redis/releases

##How to run

**Run zookeeper & kafka**

zookepper: <pre>zkserver</pre>

kafka: <pre>.\bin\windows\kafka-server-start.bat .\config\server.properties</pre>

**Handling kafka errors**

1. The input line is too long. The syntax of the command is incorrect = change folder to short name (kafka_2.15 -> kafka)
2. classpath is empty. please build the project first e.g. by running 'gradlew jarall' = you download src file

**Run redis**

Unzip downloaded file and run redis-server.exe

**Architecture**

Notes: JWTSwaggerAuditing and SampleSleuth are not included in this architecture
https://app.diagrams.net/?lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Untitled%20Diagram.drawio#R1Vtdd6o4FP01PtrFV1Af%2B%2BF0put23Xa8s5z2LYUUMkXwhlC1v36SEhQSvGouAn2qOUAgO%2FucnXOSDuzrxfqWwGV4n%2FgoGliGvx7YNwPLMidgzP5wy0ZYADByS0CwL2w7wwx%2FIGEsbsuwj9LKjTRJIoqXVaOXxDHyaMUGCUlW1dtek6j61iUMkGKYeTBSrXPs0zC3jotRcPufCAdh8WbTEFcWsLhZGNIQ%2BsmqZLKnA%2FuaJAnNfy3W1yji6BW45M%2F9sefq9sMIiukxD8xu57ENrefXm293%2F3nz6f3d019DO%2B%2FlHUaZGPB1Er%2FiYIbIOyLiw%2BmmQIMkWewj3qExsK9WIaZotoQev7piBGC2kC4i1jLZT%2FUDxTeznilal0zig29RskCUbNgtxVVHgCfoM5yI9mo3F2YBcFiaB1fYoJj%2BYNv1DiH2Q4B0AmCOAtg3BEmM42AaBxFOwzl6ySHMCKQ4iTvHcGiCwyBarYIIFBAVlFDsX3L3ZS0vgmmKPYZGSiGhqrmEF4OJbP4tN5440BegaN6sBfB5ayNae3FOk4x46LAHsS8LED1MHORX4o06a6VJATVzUtgIihi93qtRqm6ixBseEsxGtnMst8qJiTTV%2BbDFQ%2BWgIvWzjdOiH0fqJ4dF6eeTNdtB6xPJVYg0g4tlhHj4wmzauvY9u4qOZXQdvkYKYI8Z%2FogQ7QtirlWFzOw84o81g1VdVCoFoqfytT1RCa0xLT3GWnk0M2xbtHcP8kb5uQdEMAOAifjvRjj3yAhn9yrCjffw6NQIZ0o%2BbLYc4ia9o98vqadPs9GXpJksgOZYV0klRd7yriWeFeMoEe37ZUZDqy%2FKYEvKYHWuDKal6ZsnrGONwUnr2HqXNc%2FksscqQ8GtnvisKfmsLYf0o7XB3UPJtnxWTd%2BbJ6CeZphlxdiSsWkCFhN5kIGjXhEQGA0REICOCaiWQ56zLLqFFK3gpnvJ2CfO3UmGmrH2xWONdjzWOdJj3V55rJwFOEBXMqS8xDFa9li1AtAXArYkGccSsF%2BS4Uwk3mhLhlRSt9uWDDWhnWYEvcGebDk4crW8c80oXtZICWBoXBjGuJpUuCwoHHBR3pIrSqVcY2yXfbeHtamCdT3xZrlqIHNHu2jQri9vvauD4tReznw1LrhNVSqBtEZpuYBkqckoQQFOmZvjOBhYbsRwu3phEd4N6HYCS0xhAZpWuQEjHMScOGyOebC44mEcezC6FBcW2Pf541cEpfgDvnx2xdmx5KP8HDe4GoAb3ldGkzQ%2FXtCQUgB5KTdShGJUQxvrbDqhJmO8gIfiAT%2F3wHA37uY%2FuIMkb0jdlv5q6JsjUEF%2Fi3VZp%2B1W8Vdzu2e8XL7huCdLG7mCandeQbXOmI20mtZaPUtXpZkGursSUtahMOHcmqK7%2Bdp2tjo6N7G%2BWroqLUzBSHdRI62UgRy%2Bm2Mg3jyGP6H5OPUm98b3n0%2FO%2BOOfobrC%2FRv5OG0iliuBu4YhR5c228xSa4Gyzu%2BpPHe1Ks5qXDi%2Fk7meXnGS3KuxpHU%2F9zqTkJEU%2BnUrnpa8b3u%2Bimctim3skelt0p6ffr8KaccpSmcCIiVXyslC3axYSa%2FPTD81KXuAS54MGD8IxFH3QiIvFds8o1iLmO7p4LrF%2FwnHIPZ5s5bCdKAWTqdqIe0q6O%2BPyfscLauF7v5sY2fQj%2BFMv7kgFzS1Vw5AWoI4chKhzQXW3P3jTX777v%2BX7On%2F

**Note: Any services register with Eureka need 30s, it may cause 500 error when you start services and instantly run them**

Run <pre>mvn spring-boot:run</pre> on ConfigServer, Eureka, Zuul, OAuth2 and Zipkin projects first

Then <pre>mvn spring-boot:run</pre> on SampleService and QuizletService

Run: curl --location --request POST 'http://localhost:8901/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic bGVhcm5pbmdlbmdsaXNoOnRoaXNpc3NlY3JldA==' \
--form 'grant_type=password' \
--form 'scope=webclient' \
--form 'username=son2' \
--form 'password=password2'

OR run on PostMan:

POST http://localhost:8901/oauth/token

Authorization Tab: Basic Auth

Username: learningenglish & Password: thisissecret

Body Tab: form-data:
1. grant_type: password
2. scope: webclient
3. username: son2
4. password: password2

Then get the access JWT token and add to Authorization Header with value: Bearer <JWT_TOKEN> 

**Note: If you want to test token generated by OAuth2 default (not JWT type), comment out this class JWTOAuth2Config in OAuth2Service. This class is used for hooking JWT to OAuth2

**Sample request**: curl --location --request GET 'http://localhost:8081/saveQuizlet' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ3aWxsaWFtLndvb2R3YXJkIiwic2NvcGUiOlsid2ViY2xpZW50Il0sInNwZWNpYWxQcm9wZXJ0eSI6IlZlcnlJbXBvcnRhbnRQcm9wZXJ0eSIsImV4cCI6MTU4ODk5OTQ2NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJiOTk5ODMxYS03ZWZkLTQ3NTEtOTk1YS03NTZlMjAyN2Q0ZTUiLCJjbGllbnRfaWQiOiJsZWFybmluZ2VuZ2xpc2gifQ.c7AbqTIw_Ux9moexAuTVPlXIpXanfjAWDo5SxPDB51U'



