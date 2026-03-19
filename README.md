# EventHub

**EventHub** is a full-stack event search and recommendation platform built with **Java Servlets**, **Tomcat 11**, and **MySQL 8**.  
It integrates the **Ticketmaster Discovery API** to search for events based on location and keywords, persists the results to a database, and serves them through a simple front-end built with **HTML/CSS/JS**.

This project is fully deployed on **AWS EC2 (Ubuntu)**, uses **JDK 17**, loads sensitive configuration via environment variables, and manages the Tomcat service with **systemd**.

---
## 🌐 Live Demo

Frontend demo:  
👉 https://your-demo-url-here

Demo source code:  
👉 https://github.com/WenliFei417/EventHub2

**Note:**  
The original version of EventHub is a full-stack application built with Spring Boot, MySQL, and AWS.  

Due to the expiration of the AWS free tier, I created a separate lightweight demo version (`EventHub2`) to ensure the project remains accessible for recruiters and reviewers.  

This demo focuses on showcasing the core user experience and frontend interactions, while simulating backend behavior without requiring a live cloud deployment.

---

## ✨ Features

- 🔍 **Event Search**: Find events, concerts, and exhibitions by location and keyword.  
- 🎯 **Event Recommendation**: Recommend events based on location and category.  
- ⭐ **Favorites & History**: Save favorite events and view user history.  
- 🖥️ **Web Frontend**: A simple HTML/CSS/JS interface.  
- 🔐 **Environment Variables**: Securely load database and API keys.  
- ☁️ **Production Deployment**: Fully tested on AWS EC2 with MySQL and Tomcat.

---

## 🧱 Tech Stack

- **Backend**: Java 17, Maven, Servlets (Jakarta), Tomcat 11  
- **Database**: MySQL 8  
- **Frontend**: HTML, CSS, JavaScript  
- **3rd-Party API**: Ticketmaster Discovery API  
- **Deployment**: AWS EC2 (Ubuntu Linux)

---

## 📁 Project Structure

```
src/
  main/
    java/
      external/      # Ticketmaster API integration
      rpc/           # RESTful endpoints (/search, /recommendation, /history)
      db/ entity/    # Database connection and entity classes
    webapp/          # Frontend static resources
pom.xml
```

---

## ⚙️ Environment Variables

EventHub uses environment variables to configure sensitive credentials:

| Variable | Description |
|----------|-------------|
| `DB_USER` | MySQL username |
| `DB_PASS` | MySQL password |
| `TICKETMASTER_API_KEY` | Ticketmaster API key |

Example `setenv.sh` (place in `/opt/tomcat/bin/setenv.sh`):

```bash
export DB_USER="appuser"
export DB_PASS="your_password"
export TICKETMASTER_API_KEY="your_api_key"
export CATALINA_OPTS="$CATALINA_OPTS -DTICKETMASTER_API_KEY=your_api_key"
```

---

## 🗄️ Database Design

- Database: `wlproject`

| Table | Purpose |
|-------|---------|
| `users` | Store user accounts (sample user: `1111`) |
| `items` | Store events fetched from Ticketmaster |
| `categories` | Event category mapping |
| `history` | Store user favorites and history |

> ⚠️ Passwords are hashed with **MD5** (for demo only).

---

## 🚀 Run Locally

### Requirements
- JDK 17+
- MySQL 8+
- Ticketmaster API Key

### 1. Clone the repo

```bash
git clone https://github.com/WenliFei417/EventHub.git
cd EventHub
```

### 2. Setup Database

```bash
mysql -u root -p -e "CREATE DATABASE wlproject;"
mysql -u root -p wlproject < ~/wlproject.sql
mysql -u root -p -e "CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'password';"
mysql -u root -p -e "GRANT ALL ON wlproject.* TO 'appuser'@'localhost'; FLUSH PRIVILEGES;"
```

### 3. Set Environment Variables

```bash
export DB_USER=appuser
export DB_PASS=password
export TICKETMASTER_API_KEY=your_api_key
```

### 4. Build Project

```bash
./mvnw clean package -DskipTests
```

### 5. Deploy to Tomcat

Copy the `.war` file to Tomcat's `webapps/`:

```bash
cp target/EventHub.war /opt/tomcat/webapps/ROOT.war
```

Restart Tomcat:

```bash
sudo systemctl restart tomcat
```

---

## ☁️ Deploy to AWS EC2

1. Launch an EC2 instance (Ubuntu, t2.micro, Free Tier)  
2. Install Java, MySQL, and Tomcat 11  
3. Configure security group (open 22 and 80, close 3306)  
4. Use `mysqldump` and `scp` to migrate the database  
5. Upload the WAR file and restart Tomcat  
6. Access via browser: `http://3.16.186.126/`

---

## 🔌 API Endpoints

| Path | Method | Description |
|------|--------|-------------|
| `/search?lat=xx&lon=yy&keyword=music` | GET | Search events nearby |
| `/recommendation?lat=xx&lon=yy` | GET | Get event recommendations |
| `/history?user_id=1111` | GET | Retrieve user history |

---

## 🧪 Example Requests

```bash
# Search
curl -i "http://3.16.186.126/search?lat=37.38&lon=-122.08&keyword=music"

# Recommendation
curl -i "http://3.16.186.126/recommendation?lat=40.7128&lon=-74.0060"

# History
curl -i "http://3.16.186.126/history?user_id=1111"
```

---

## 🔐 Authentication (Demo)

Currently, the app uses a single sample user (`user_id = 1111`).  
All users share the same session.  
For production, implement session-based authentication or token-based auth.

---

## 🛠️ Troubleshooting

- **Access denied**: Check MySQL credentials and privileges.  
- **Public Key Retrieval error**: Switch to `mysql_native_password` or add `allowPublicKeyRetrieval=true` to JDBC URL.  
- **404 error**: Ensure the WAR file is named `ROOT.war` and Tomcat is running.  
- **Empty response**: Some locations may not have Ticketmaster data.

---

## 📜 License

MIT License – Free to use for learning, research, and modification.

---

## 📌 Future Improvements

- User registration & authentication  
- Personalized recommendation engine  
- Modern frontend (React / Vue)  
- Dockerized deployment
