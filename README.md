# Ultimate DevOps 🚀

A simple Java console application built with Maven and Java 21, designed to demonstrate an end-to-end CI/CD pipeline using Jenkins, Docker, SonarQube,Trivy , Kubernetes and GitHub.

---

## 📦 Project Structure
---
## 💡 Features

- ✅ **CI/CD Pipeline** with Jenkins
- 🐳 **Dockerized** using multi-stage builds
- 🔐 **Security scanning** with Trivy
- 📊 **Code quality & coverage** via SonarQube and JaCoCo
- ⚙️ **Kubernetes-ready** with deployment/service YAMLs
- 🎯 **Parameterized builds** (`dev`, `staging`, `production`)
- ☁️ **Push Docker images** to Docker Hub
- 📦 **GitHub Webhook** support for auto-builds

---

## 🛠️ Prerequisites

- Java 21
- Maven
- Docker
- [Trivy](https://aquasecurity.github.io/trivy/)
- Kubernetes (e.g. Minikube or remote cluster)
- Jenkins (with Docker, Git, SonarQube plugins)
- Docker Hub account
- GitHub repository

---

## 🚀 Build & Test Locally

```bash
chmod +x build.sh
./build.sh

java -jar target/hello-jenkins.jar

🐳 Docker Build & Push
docker build -t yourdockerhub/java:latest .
docker push yourdockerhub/java:latest

🔐 Trivy Security Scan
Install Trivy:
https://aquasecurity.github.io/trivy/v0.18.3/installation/
Run scan:
trivy image yourdockerhub/java:latest --output trivy-report/report.txt


