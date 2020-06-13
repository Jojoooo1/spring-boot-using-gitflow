#!/bin/bash
set -e # exit on first error (used for return)

current_dir=$PWD

[[ ! -x "$(command -v minikube)" ]] && echo "minikube not found" && exit 1

# minikube delete
# minikube start
# minikube start --cpus 4 --memory 8096

# Sets minikube local daemon
eval $(minikube docker-env)

cd ..

# Start skaffold
skaffold dev --port-forward \
  --verbosity=info --tail=true \
  --filename=./skaffold/skaffold.yaml
