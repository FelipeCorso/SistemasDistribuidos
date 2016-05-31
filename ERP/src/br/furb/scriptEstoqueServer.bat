echo off
cls
echo "SCRIPT .BAT para levantar o servidor EstoqueRmi"

REM deve colocar o diretorio que se encontra os .class
SET DIRETORIO="C:\Users\Felipe\git\SistemasDistribuidos\ERP\bin\"

REM deve colocar o diretorio que se encontra o java jdk
SET dirJava="C:\Program Files\Java\jdk1.8.0_60\bin"

chdir %DIRETORIO%

set path=%dirJava%;

set classpath=.

REM A partir do Java 1.7, não é necessário criar Stubs e Skelettons.
REM rmic br.furb.rmi.estoque.server.EstoqueServer
pause

REM Dir do joda
start rmiregistry -J-classpath -J"..\lib\joda-time-2.9.4.jar"