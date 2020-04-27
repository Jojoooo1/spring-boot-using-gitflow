# Git-flow model using Github Actions
<div align="center">
<img src="https://nvie.com/img/git-model@2x.png" height="600">
</div>

## Types of branches

The Git-flow model uses 5 types of branches to aid parallel development between team members.

### Long lived branches

- `origin/master`
  - Always reflects a production-ready state.
- `origin/develop`
  - Always reflects a state with the latest delivered development changes for the next release.

### Short  lived branches

- `origin/feature-*`:
  - Branch off from develop used to create new feature for the application
- `origin/release-v*`
  - Branch off from develop when a new group of features are ready to be deployed to master
- `origin/hotfix-*`
  - Branch off from master, when a quick fix needs to be deployed to master
