@echo off
echo pushing changes to GitHub

git status
git add .
git status
git commit -m "Functionalities Added, Bug Fixes"
git push

echo Done!
exit