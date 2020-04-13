#!/bin/bash
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
  echo "Creating branch $NEW_RELEASE..."
  git checkout -b $NEW_RELEASE
  git push origin $NEW_RELEASE
else
  echo
  echo "Exiting..."
  exit 0
fi

# validate release
# create branch release-vX.X
