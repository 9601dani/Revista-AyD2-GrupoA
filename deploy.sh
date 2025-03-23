#!/bin/bash

set -e

BRANCH_NAME=$1
USER=$VM_USERNAME

if [ "$BRANCH_NAME" == "develop" ]; then
  echo "DEVELOP"
  ENV="development"
  TARGET_VM=$DEV_IP
elif [ "$BRANCH_NAME" == "main" ]; then
  echo "MAIN"
  ENV="production"
  TARGET_VM=$PROD_IP
else
  echo "THIS BRANCH CANNOT BE DEPLOYED"
  exit 0
fi

echo "BUILDING BACKEND"
cd app-backend
mvn clean package -DskipTests

mkdir -p ../deploy/backend
cp gateway/target/*.jar ../deploy/backend/gateway.jar

for MS in ms-*; do
  cp "$MS/target/"*.jar "../deploy/backend/${MS}.jar"
done
cd ..

echo "BUILDING FRONTEND"
cd app-frontend
npm run build --configuration $ENV
cd ..

mkdir -p deploy/frontend
cp -r app-frontend/dist/* deploy/frontend/

echo "SENDING COMPILED FILES"
scp -r deploy "$USER"@"$TARGET_VM":/home/"$USER"/

