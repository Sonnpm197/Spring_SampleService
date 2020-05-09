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
https://app.diagrams.net/?lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Untitled%20Diagram.drawio#R1Vtdd6o4FP01PtrFV1Af%2B%2BF0put23Xa8s5z2LYUUMkXwhlC1v36SEhQSvWIuAn2qOUAgO%2FucnXOSDuzrxfqWwGV4n%2FgoGliGvx7YNwPLMidgzP5wy0ZYADByS0CwL2w7wwx%2FIGEsbsuwj9LKjTRJIoqXVaOXxDHyaMUGCUlW1dtek6j61iUMkGKYeTBSrXPs0zC3jotRcPufCAdh8WbTEFcWsLhZGNIQ%2BsmqZLKnA%2FuaJAnNfy3W1yji6BW45M%2F9ceDq9sMIimmdB2a389iG1vPrzbe7%2F7z59P7u6a%2BhnffyDqNMDPg6iV9xMEPkHRHx4XRToEGSLPYR79AY2FerEFM0W0KPX10xAjBbSBcRa5nsp%2FqB4ptZzxStSybxwbcoWSBKNuyW4qojwBP0GU5Ee7WbC7MAOCzNgytsUEx%2FsO16hxD7IUA6ATBHAewbgiTGcTCNgwin4Ry95BBmBFKcxJ1jODTBcRCtVkEECogKSij2L7n7spYXwTTFHkMjpZBQ1VzCi8FENv%2BWG08c6AtQNG%2FWAvi8tRGtgzinSUY8dNyD2JcFiB4nDvIr8UadtdKkgD1zUtgIihi93qtRat9EiTc8JJiNbOdYbpUTE2mq82GLh8pBRepnG6dFP47UTw6L0s8na7aD1ieSqxBpBhfLCPHwhdm0de17dhUdy%2Bg6fI0UwB4z%2FBEh2hfEXKsKmdl5xB9rBqt9UakUiJ7K1w5EJbTGtPQYa%2BXRzLBt0d49yBvl5x4QwQwAJuK%2FG%2BHcmhHO7lWEGx%2Fg0akRzpR82Gw5xE16R79fUk%2BfZqMvSTNZAM2xrpJKirzlXUs8K8ZRItr3y4yGVl%2BUwZaUwepcGUxL0zdPWMcag5PWsftd1jyTy9ZVhoJbPfFZU%2FJZWw7ptbXBPUDJtnxWTd%2BbJ6CeZphlxdiSsWkCFhN5lIGjXhEQGA0REICOCaiWQ56zLLqFFK3gpnvJOCTO3UmGmrH2xWONdjzWqemxbq88Vs4CHKArGVJe4hgte6xaAegLAVuSjLoE7JdkOBOJN9qSIZXU7bYlQ01opxlBb7AnWw6OXC3vXDOKlzVSAhgaF4YxriYVLgsKR1yUt%2BSKUinXGNtl3%2B1hbapgXU%2B8Wa4ayNzRLhq068tb7%2BqgOHWQM1%2BNC25TlUogrVFaLiBZajJKUIBT5uY4DgaWGzHcrl5YhHcDup3AElNYgKZVbsAIBzEnDptjHiyueBjHHowuxYUF9n3%2B%2BBVBKf6AL59dcXYs%2BSg%2Fxw2uBuCG95XRJM2PFzSkFEBeyo0UoRjtoY11Np1QkzFewEPxgJ97YLgbd%2FMf3EGSN6RuS3819M0RqKC%2Fxbqs03ar%2BKu53TNeLt9w3JOljVxBtTuvoFqtZCNWj%2FNhq%2B4Kpmf5sEQloLvtYY6kjtpWLd3t3bbz4VHnDOxZQiwtfcFId9kkrcWBLBDNMRBvHsOf0HycepN74%2FvPJ2f88c9QXUP%2FjXycNqEWijTsYUjt4mmbefBeoKzzeyrPjqtqYVw4v5Mbn17TktyrsbT4MPc60xAp9Fu6NVVL3hk%2BX011L4pt7MLpbQOfn36%2FCmn1FKUzAZHSN%2BXsom7erSTwZ6afmvY9wCVPN4wfBOKoeyGR14ptnoLci5ju%2BeOyZ2octDjkzVoK04FaOJ2qhbRvob8DJ%2B%2BktKwWujvAjZ1yr8OZfnNBLplqrxyAtARx5CRCmwusufvXnvz23X9I2dP%2FAQ%3D%3D

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



