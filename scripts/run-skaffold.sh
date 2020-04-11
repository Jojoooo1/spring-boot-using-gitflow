#!/bin/bash

skaffold run \
  --namespace=java-demo \
  --port-forward=false \
  --status-check=true \
  --profile=minikube \
  --tag=test
