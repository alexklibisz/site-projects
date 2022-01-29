#!/bin/bash
set -e

echo "Deleting existing deployments..."
kubectl delete deployment --wait=true --all
kubectl delete replicaset --wait=true --all

echo "Creating new deployment..."
kubectl apply -f $1

echo "Waiting for deployment to be available..."
kubectl rollout status --watch deployment/nginx
kubectl wait --for=condition=available --timeout=5s deployment/nginx

echo "Restarting the deployment..."
kubectl get replicaset

kubectl rollout restart deployment/nginx &> /dev/null

while true
do
  echo "---"
  kubectl rollout status deployment/nginx --timeout=1s &> /dev/null && break || true
  kubectl get replicaset | tail -n +2
done

kubectl get replicaset | tail -n +2