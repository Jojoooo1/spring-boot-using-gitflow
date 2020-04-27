#!/bin/bash
current_dir=$PWD
# Sets minikube local daemon
eval $(minikube docker-env)
# Sets kubectl namespace
sudo kubectl config set-context minikube --namespace java-demo
#
cd ..
# Start skaffold
skaffold dev --port-forward \
  --verbosity=info --tail=true \
  --filename=skaffold.yaml
