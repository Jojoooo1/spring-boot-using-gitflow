#!/bin/bash
set -ev # exit on first error (used for return)

SHA_REF="2fa37293f8a3ce627d1ac3d9a558edea97073d39"
curl -X GET -H "Authorization: token c71bb3735d9dbcbe2fd1b62dc2a2176ef7d18b1b" -I \
  "https://api.github.com/repos/Jojoooo1/spring-boot-using-gitflow/commits/$SHA_REF/status"

echo '{"description": "Test, build and deploy release candidate image", "target_url":' "$SHA_REF"'}'
