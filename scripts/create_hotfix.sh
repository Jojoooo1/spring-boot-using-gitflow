#!/bin/bash
set -e # exit on first error (used for return)

# Make sure to branch from master
echo
echo ">>> Pulling last master branch"
git checkout master
git pull origin master

echo
echo ">>> Pulling last tags"
git fetch --prune --tags # git tag -d $(git tag) # delete all local tags

echo
echo "################################"
echo "##  Starting hotifx creation ##"
echo "################################"
echo
read -r -p "What is the name of the branch you want to create (should start with hotfix-):  " BRANCH_NAME
read -r -p "Are you sure you want to create the branch '$BRANCH_NAME' [Y/n]:  " RESPONSE
echo

if [[ $RESPONSE =~ ^([yY][eE][sS]|[yY])$ ]] && [[ $BRANCH_NAME == hotfix-* ]]; then

  echo
  echo ">>>>> Creating branch '$BRANCH_NAME' from master..."
  git checkout -b $BRANCH_NAME master

else

  echo "'$BRANCH_NAME' is not a valid hotfix branch name"
  exit 1

fi
