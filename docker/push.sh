#!/bin/bash
set -ex

ECR_REPO_URI="577381722483.dkr.ecr.eu-west-1.amazonaws.com/hello-world"
SERVICE_NAME="java-backend-template"

echo "> building docker image for service: $SERVICE_NAME"
docker build -t $SERVICE_NAME -f ./docker/Dockerfile .

echo "> tagging image from $SERVICE_NAME:latest to $ECR_REPO_URI:latest"
docker tag $SERVICE_NAME:latest $ECR_REPO_URI:latest

echo "> login docker and push image to ECR"
aws ecr get-login --no-include-email --region eu-west-1 | bash

echo "> pushing image $ECR_REPO_URI:latest"
docker push $ECR_REPO_URI:latest
