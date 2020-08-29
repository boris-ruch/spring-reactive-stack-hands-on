# Run k6 script (Load Test)
```k6 run -vu 100 --duration 30s k6-skript-webflux.js```
```k6 run -vu 100 --duration 30s k6-skript-mvc.js```
https://test-api.k6.io/
https://k6.io/blog/load-testing-with-postman-collections

# Conclusion
Scenario:
Tomcat server with thread pool=4 (in order to create enough load from one machine)
- 100 concurrent users
- During 30 seconds
- Repository is blocking for 50ms to retrieve all users

Webflux: processed 6'600 requests
MVC: processed 360 requests

Webflux can process 18x more requests than servlet stack!