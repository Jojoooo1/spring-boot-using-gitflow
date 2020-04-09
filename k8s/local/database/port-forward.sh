#!/bin/bash

POD=$(kubectl get pods -l app=mysql-test -o custom-columns=:metadata.name)
echo $POD
kubectl port-forward $POD 3306:3306
