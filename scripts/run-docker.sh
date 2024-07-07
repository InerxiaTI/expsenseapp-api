#!/bin/bash

# Define variables
IMAGE_NAME="expense-i"
CONTAINER_NAME="$IMAGE_NAME-con"

echo $IMAGE_NAME
echo $CONTAINER_NAME

# Run container $CONTAINER_NAME
docker run --name $CONTAINER_NAME --restart=always --network postgres_intranet-docker2 -d -p 8080:8080 $IMAGE_NAME:latest
#docker run --name $CONTAINER_NAME --network postgres_intranet-docker2  -p 3001:3000 $IMAGE_NAME:latest