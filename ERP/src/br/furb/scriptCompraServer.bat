echo off
cls
echo "SCRIPT .BAT para levantar o servidor Compras Corba"

REM deve colocar o diretorio que se encontra os .class
SET DIRETORIO="C:\Git\SistemasDistribuidos\ERP\src\"

REM deve colocar o diretorio que se encontra o java jdk
SET dirJava="C:\Program Files\Java\jdk1.8.0_91\bin"

chdir %DIRETORIO%

set path=%dirJava%;

idlj -fall br\furb\corba\Compra.idl

set classpath=.

pause
start orbd