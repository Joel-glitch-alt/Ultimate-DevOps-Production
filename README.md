# Ultimate DevOps 🚀

A simple Java console application built with Maven and Java 21, designed to demonstrate an end-to-end CI/CD pipeline using Jenkins, Docker, SonarQube,Trivy , Kubernetes and GitHub.

---

## 📦 Project Structure

├── build.sh # Local build and test script
├── deploy.sh # Docker-based deploy script
├── Dockerfile # Multi-stage Dockerfile (build + runtime)
├── Jenkinsfile # Declarative pipeline for Jenkins CI/CD
├── k8s/
│ ├── deployment.yaml # Kubernetes Deployment spec
│ └── service.yaml # Kubernetes Service spec
├── trivy-report/ # Trivy output folder (optional)
├── main.java # Java application file
├── mainTest.java # JUnit test file
├── pom.xml # Maven build file
└── README.md # This file


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


