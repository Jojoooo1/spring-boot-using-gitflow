#!/bin/bash

set -e # exit on first error

# Make sure we are on branch develop
git checkout develop
git pull origin develop
# Get latest tag
LATEST_TAG=$(git describe --tags $(git rev-list --tags --max-count=1))
echo
echo "################################"
echo "##  Starting release creation ##"
echo "################################"
echo
read -r -p "Enter the release version you want to create (latest: $LATEST_TAG):  " NEW_RELEASE
echo
read -r -p "Are you sure you want to create the release \"$NEW_RELEASE\" [Y/n]:  " RESPONSE

if [[ $RESPONSE =~ ^([yY][eE][sS]|[yY])$ ]]; then
  echo
  echo "Creating branch release-$NEW_RELEASE..."
  echo
  git checkout -b release-$NEW_RELEASE
  git push origin release-$NEW_RELEASE
else
  echo
  echo "Action cancelled exiting..."
  exit 0
fi

# validate release
# create branch release-vX.X
