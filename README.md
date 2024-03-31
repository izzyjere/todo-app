# REQUIREMENTS
* JDK 21
* Node JS
* Vue Cli
* To run the api as a container docker will be required.
# PROJECT STRUCTURE
* Springboot Back-end
* Vue JS Front-end
# FEATURES INCLUDED
* Spring Security 6 with JWT Authentication
* Unit tests using JUnit + Mockito
* State management using Vuex
* Database : SQlite (for portability)
* Docker support
* Open API Swagger UI
# STEPS
* Initialize Client inside (frontend folder) <code>npm install</code>.
* Ensure that the api is running on port 8080. Client dev server runs on port 8085
* Run the api using IntelliJ or Docker while in the project root. <code> docker build -t todo-app . </code> & then <code> docker run -p 8080:8080 todo-app </code>
* Run the client inside (frontend folder) <code>npm run serve</code>
