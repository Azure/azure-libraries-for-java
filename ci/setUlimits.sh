#!/usr/bin/env bash

echo "Current limits :"
ulimit -n

ulimit

echo "Setting new limits"
ulimit -S unlimited
ulimit -H unlimited

ulimit -n 65535

echo "updated limits"
ulimit

ulimit -n