#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/

if [ -d $REPOSITORY/myapp ]; then
    rm -rf $REPOSITORY/app
fi
mkdir -vp $REPOSITORY/app
