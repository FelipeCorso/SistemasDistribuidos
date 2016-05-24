echo off
cls
echo "SCRIPT .BAT para levantar o servidor EstoqueRmi"

REM deve colocar o diretorio que se encontra os .class
SET DIRETORIO="C:\Git\SistemasDistribuidos\ERP\bin\"

REM deve colocar o diretorio que se encontra o java jdk
SET dirJava="C:\Program Files\Java\jdk1.8.0_91\bin"

chdir %DIRETORIO%

set path=%dirJava%;

set classpath=.

rmic br.furb.rmi.estoque.server.EstoqueServer
pause

start rmiregistry