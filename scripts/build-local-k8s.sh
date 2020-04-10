#!/bin/bash
current_dir=$PWD
# Sets minikube local daemon
eval $(minikube docker-env)
#
kubectl create namespace java-demo
# push image to minikube local daemon
# mvn clean compile jib:dockerBuild -P local-k8s
#
#
# kubectl apply -f k8s/stages/local/db.yaml
# kubectl apply -f k8s/stages/local/rbac.yaml
# kubectl apply -f k8s/stages/local/app.yaml
# kubectl rollout restart deployment java-demo-backend
# cd k8s/stages/local
# kubectl apply -f mysql-test.yaml
# sleep 8
