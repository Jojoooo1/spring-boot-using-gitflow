#!/bin/bash
set -e # exit on first error (used for return)

# Make sure to branch from develop
echo
echo ">>> Pulling last develop branch"
git checkout develop
git pull origin develop

echo
echo ">>> Pulling last tags"
git fetch --prune --tags # git tag -d $(git tag) # delete all local tags

# Get latest tag
LATEST_TAG=$(git describe --tags $(git rev-list --tags --max-count=1))
TAG_LIST=($(echo $LATEST_TAG | tr '.' ' '))

V_MAJOR=${TAG_LIST[0]}
V_MINOR=${TAG_LIST[1]}
V_PATCH=${TAG_LIST[2]}

NEW_RELEASE_VERSION=${V_MAJOR}.$((${V_MINOR} + 1)).0

echo
echo " ################################"
echo "##  Starting release creation ##"
echo "################################"
echo

read -r -p "Last release version was '$LATEST_TAG', do you want to create release '$NEW_RELEASE_VERSION' [Y/n]:  " RESPONSE
echo

# Bump NEW_RELEASE_VERSION automatically based on latest tag
if [[ $RESPONSE =~ ^([yY][eE][sS]|[yY])$ ]]; then

  BRANCH_NAME="release-$NEW_RELEASE_VERSION"
  echo
  echo ">>>>> Creating branch '$BRANCH_NAME'..."
  echo
  git checkout -b $BRANCH_NAME develop

# User define NEW_RELEASE_VERSION
else

  read -r -p "Enter the release version you want to create (latest: $LATEST_TAG): " NEW_RELEASE_VERSION
  read -r -p "Are you sure you want to create the release '$NEW_RELEASE_VERSION' [Y/n]:  " RESPONSE

  if [[ $RESPONSE =~ ^([yY][eE][sS]|[yY])$ ]]; then

    TAG_LIST=($(echo $NEW_RELEASE_VERSION | tr '.' ' '))
    [[ "${#TAG_LIST[@]}" -ne 3 ]] && echo "'$NEW_RELEASE_VERSION' is not a valid semantic version" && exit 1

    BRANCH_NAME="release-$NEW_RELEASE_VERSION"
    echo ">>>>> Creating branch '$BRANCH_NAME' from develop..."
    git checkout -b $BRANCH_NAME develop

  else

    echo
    echo "Action cancelled exiting..."
    exit 0

  fi

fi
