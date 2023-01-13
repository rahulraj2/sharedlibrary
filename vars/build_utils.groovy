#!/usr/bin/env groovy

def build(Map config){
    println config.ENV_APPLICATION_NAME
    println config.ENV_NAMESPACE_NAME
    echo "Hello Pipeline"
}