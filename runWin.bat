set PWD=%~dp0
set APP_FOLDER="%PWD%\target\"
set "APP_JAR_NAME="
for %%i in (%APP_FOLDER%*.jar) do set APP_JAR_NAME=%%i
if defined APP_JAR_NAME (
   echo file is here: %APP_JAR_NAME%
) else (
   echo file is not here
)
set JAVA_OPTS= --enable-preview -agentlib:jdwp=transport=dt_socket,server=y,address=8011,suspend=n


SET JAVA_EXE="%JAVA_HOME%\bin\java.exe"
%JAVA_EXE% %JAVA_OPTS% -jar %APP_JAR_NAME%
