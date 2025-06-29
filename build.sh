#!/bin/bash

# Build script for HelloJenkins project with flat root structure
# This script helps organize the build process

echo "🚀 Starting HelloJenkins build process..."

# Create Maven directory structure if it doesn't exist
echo "📁 Creating Maven directory structure..."
mkdir -p src/main/java/com/jenkins
mkdir -p src/test/java/com/jenkins

# Copy files to proper Maven structure (if they exist in root)
if [ -f "main.java" ]; then
    echo "📄 Copying main.java to Maven structure..."
    cp main.java src/main/java/com/jenkins/HelloJenkins.java
fi

if [ -f "mainTest.java" ]; then
    echo "📄 Copying mainTest.java to Maven structure..."
    cp mainTest.java src/test/java/com/jenkins/HelloJenkinsTest.java
fi

# Run Maven build
echo "🔨 Running Maven build..."
mvn clean compile

echo "🧪 Running tests..."
mvn test

echo "📦 Packaging application..."
mvn package

echo "📊 Generating test reports..."
mvn jacoco:report

echo "✅ Build completed successfully!"
echo "📋 Generated files:"
echo "   - target/hello-jenkins.jar (executable JAR)"
echo "   - target/site/jacoco/index.html (coverage report)"
echo "   - target/surefire-reports/ (test reports)"

# Optional: Run the application
echo ""
read -p "🎯 Do you want to run the application? (y/n): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🏃 Running HelloJenkins application..."
    java -jar target/hello-jenkins.jar
fi

echo "🎉 All done!"