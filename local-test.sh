#!/bin/bash

kubectl delete namespace ignite-test
sleep 1
kubectl create namespace ignite-test
kubectl apply -f ignite-account-role.yaml
kubectl apply -f ignite-service-account.yaml
kubectl apply -f ignite-role-binding.yaml
kubectl apply -f test-service-service.yaml
kubectl apply -f test-service-configmap.yaml
kubectl apply -f test-service-deployment.yaml
kubectl apply -f test-service-autoscaler.yaml