#!/bin/bash
# Push project to master
git add .
git commit -m "first commit"
git push origin master
# Create and push tag
git tag -a v1.0.0 -m "First release"
git push origin v1.0.0
# Create Develop branch
git checkout -b develop
