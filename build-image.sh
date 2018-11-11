#!/usr/bin/env bash

tag_name=automator

echo "Assembling fat jar"
sbt assembly

echo "Building image"
docker build --tag ${tag_name} .

echo "Pushing image"
#docker push ${tag_name}

echo "Image has been successfully pushed"