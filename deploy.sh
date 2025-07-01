#!/bin/bash

# Docker-based deployment script

echo "🚀 Starting Docker deployment for environment: ${ENV:-dev}"

# Image and container naming
IMAGE="addition1905/java:${BUILD_NUMBER:-latest}"
CONTAINER_NAME="java-${ENV:-dev}"

# Optional: Customize exposed port for each env
PORT=8080
if [ "$ENV" = "staging" ]; then
  PORT=8081
elif [ "$ENV" = "production" ]; then
  PORT=8082
fi

# Stop and remove any existing container with the same name
echo "🛑 Cleaning up old container..."
docker stop "$CONTAINER_NAME" 2>/dev/null || true
docker rm "$CONTAINER_NAME" 2>/dev/null || true

# Run the new container
echo "🐳 Starting Docker container '$CONTAINER_NAME' on port $PORT..."
docker run -d --name "$CONTAINER_NAME" -p $PORT:8080 "$IMAGE"

echo "✅ Deployed $IMAGE as container '$CONTAINER_NAME' on port $PORT"
