#!/bin/bash
set -e # exit on first error (used for return)

current_dir=$PWD

[[ ! -x "$(command -v minikube)" ]] && echo "minikube not found" && exit 1
[[ ! -x "$(command -v skaffold)" ]] && echo "skaffold not found" && exit 1

# Sets minikube local daemon for preventing to pull from external source
eval $(minikube docker-env)

cd ..

# Start skaffold
skaffold dev --port-forward \
  --verbosity=info --tail=true \
  --filename=./skaffold/config.yaml
