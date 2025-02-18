# **Idea Portal**

## **Project Description**  
The **Idea Portal** is a centralized, collaborative platform designed to empower employees to share innovative ideas effortlessly. This portal streamlines the idea management process, boosts participation, and fosters a culture of innovation within the organization. By providing a structured environment for idea generation, evaluation, and collaboration, the platform enhances transparency and drives continuous improvement, ultimately giving the organization a competitive edge.

---

## **Technology Stack**  
- **Backend:** JAVA 21  
- **Frontend:** React.js (Optional for UI development)  
- **Database:** MySQL  
- **ORM:** Spring Data JPA  
- **Authentication:** Client ID Authentication  
- **Email Service:** SMTP (for notifications and approvals)  
- **Logging:** Audit Logs stored in AdminLevelUser database  

---

## **Features**  
- **Idea Submission:** Employees can easily submit their ideas.  
- **Idea Evaluation:** Ideas are evaluated through voting and feedback.  
- **Collaboration:** Users can comment on and discuss ideas.  
- **Notifications:** Email notifications for idea status updates.  
- **Admin Dashboard:** Admins can review, approve, or archive ideas.  

---

## **Prerequisites**  
Before setting up the project, ensure you have the following:  

1. **Install a Code Editor or IDE**  
   - Recommended: [JetBrains Rider](https://www.jetbrains.com/rider/)

2. **Install MySQL using Docker**  
   - Follow this guide: [Install MySQL Server using Docker](https://www.datacamp.com/tutorial/set-up-and-configure-mysql-in-docker)

3. **Install JAVA 21 SDK**  
   - Install from [Oracle Official Download Site](https://www.oracle.com/ng/java/technologies/downloads/)
      
4. **Install MAVEN**  
   - Install from [How to install Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)

---

## **Running the Application**  
Once the application has been set up properly, follow the steps to run the application:

1. **Build the Application**  
    ```bash
    mvn clean
    mvn install
    ```

2. **From the application home directory, change directory to the target folder**  
    ```bash
    cd target
    ```

3. **Execute the build file**  
    ```bash
    java -jar idea-portal-0.0.1-SNAPSHOT.jar
    ```

---

## **API Documentation**  
### **Swagger UI**  
Once the application is running, access the API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

### **Postman**  
- You can also import the API collection into Postman for testing.

---
