#!/bin/bash
current_dir=$PWD
# Sets minikube local daemon
eval $(minikube docker-env)
# Creates namespace
kubectl create namespace java-demo
# Sets kubectl namespace
sudo kubectl config set-context minikube --namespace java-demo
#
cd ..
# Start skaffold
skaffold dev -v info --port-forward --rpc-http-port 44489 --filename skaffold.yaml -p minikube
