@echo off
:debut
echo Apuuyer sur une touches pour copier !
pause>nul
echo Fichiers entrain d'etre copier !
XCOPY /S /Y ".\src\web\*.rweb" ".\bin\web\"
XCOPY /S /Y ".\src\web\*.html" ".\bin\web\"
XCOPY /S /Y ".\src\web\*.css" ".\bin\web\"
XCOPY /S /Y ".\src\web\*.js" ".\bin\web\"
echo Fichiers copier !
echo -----------------
goto debut