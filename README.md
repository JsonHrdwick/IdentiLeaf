# 🌿 IdentiLeaf

IdentiLeaf is a user-friendly app designed to help you identify local trees in your area through a simple and engaging Q&A format. Powered by AI, it provides detailed information on the trees you discover. Whether you’re a botany enthusiast, student, or just curious about the trees around you, IdentiLeaf makes tree identification fun and educational!

## 🚀 Features

Interactive Q&A: Answer a series of questions about the tree you’ve encountered—such as leaf shape, bark texture, and tree size—and IdentiLeaf will help identify it.
AI-Generated Information: Once a tree is identified, IdentiLeaf provides comprehensive, AI-curated information about the species, including ecological significance, habitat, common uses, and more.
Local Species Database: Tailored to your region, IdentiLeaf focuses on trees local to your area, ensuring relevant and accurate identification results.
User-Friendly Design: Simple, clean, and intuitive interface that anyone can use—whether you're a nature lover or someone curious about trees in their backyard.

## 🌳 How It Works

Start a Tree Search: Open the app and login.

Answer Questions: Based on your input, IdentiLeaf will ask you relevant questions to narrow down the tree species.
Get Results: After the Q&A, you’ll receive an AI-generated summary of the identified tree, including facts, images, and more.
Explore Further: Dive into detailed species info with an AI-powered knowledge base that goes beyond just identification.

## 📱 Screenshots
![image](https://github.com/user-attachments/assets/4a9ac824-5c73-40b0-8e15-0b25d4229036)
![image](https://github.com/user-attachments/assets/d192f30d-beec-4afc-ac77-fdbc33643ae2)


## 🛠️ Technologies Used

React Native for the mobile app interface

Java with SpringBoot for the backend 

OpenAI for AI model integration

MySQL using AWS for tree and user data storage

## 🌟 Acknowledgements

Tree data sourced from South Carolina Foresty Commission

AI integration powered by OpenAI

**Database Design and Material Research:**
- Ian Rastogi

**Frontend Design:**
- Tehytan “TK” Retuyan

## Installation

Frontend component: From ``IdentiLeaf-Master/app`` directory, run ``npm install`` in the terminal to install the most recent packages for node and expo. Run the module with ``npm start``.
Backend component: Navigate to ``IdentiLeaf-Master/core/src/main/java/org/identileaf/identileafcore/IdentiLeafCoreApplication``. Run the application here.
For security reasons, application.properties is missing from the GitHub repository. This file contains database credentials and the AI API key.
This file lives in `core/src/main/resources/application.properties` The sensitive data has been omitted.
```
spring.application.name=IdentiLeaf-Core
spring.datasource.url=***
spring.datasource.username=***
spring.datasource.password=***
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.web.resources.add-mappings=false
server.error.whitelabel.enabled=false

server.servlet.session.timeout=5m
spring.web.resources.static-locations=classpath:/static/

spring.ai.openai.api-key=***
```
