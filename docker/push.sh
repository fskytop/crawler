#!/bin/bash
set -e

get_commit_count() {
  git rev-list --all --count
}

source secrets/docker-hub.env
echo "> Logging docker hub with user: ${DOCKER_USERNAME}"

echo "${DOCKER_PASSWORD}" | \
docker login --username="${DOCKER_USERNAME}" --password-stdin

echo "> Pushing image to docker hub..."
docker tag nginx-apigw-prod:latest sunzhongmou/oneapi-nginx-gateway:latest
docker push sunzhongmou/oneapi-nginx-gateway:latest

docker tag sunzhongmou/oneapi-nginx-gateway:latest "sunzhongmou/oneapi-nginx-gateway:$(get_commit_count)"
docker push "sunzhongmou/oneapi-nginx-gateway:$(get_commit_count)"
