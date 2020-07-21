#!/bin/bash

npm run build
rm -r ../server/src/main/resources/public/*
cp -r build/* ../server/src/main/resources/public
echo "DONE"