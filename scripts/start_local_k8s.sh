

current_dir=$PWD

if [[ ! -x "$(command -v minikube)" ]]; then
  echo "minikube not found"
  exit 1
fi

# minikube delete
# minikube start
# minikube start --cpus 4 --memory 8096

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
