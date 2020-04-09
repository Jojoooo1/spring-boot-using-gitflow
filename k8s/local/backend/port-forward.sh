#!/bin/bash

POD=$(kubectl get pods -l app=java-demo-backend -o custom-columns=:metadata.name)
echo $POD
kubectl port-forward $POD 8080:8080
