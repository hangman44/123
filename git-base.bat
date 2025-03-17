@echo off
cls
git fetch --all
git reset --hard origin/main
git pull origin main
timeout /t 5
git-base.bat