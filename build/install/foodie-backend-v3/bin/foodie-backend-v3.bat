@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  foodie-backend-v3 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and FOODIE_BACKEND_V3_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Dio.ktor.development=false"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\foodie-backend-v3-0.0.1.jar;%APP_HOME%\lib\ktor-server-content-negotiation-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-server-locations-jvm-2.1.3.jar;%APP_HOME%\lib\koin-ktor-3.2.2.jar;%APP_HOME%\lib\ktor-server-auth-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-server-data-conversion-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-server-netty-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-server-host-common-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-server-sessions-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-server-core-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-serialization-gson-jvm-2.1.3.jar;%APP_HOME%\lib\exposed-jdbc-0.40.1.jar;%APP_HOME%\lib\exposed-dao-0.40.1.jar;%APP_HOME%\lib\koin-logger-slf4j-3.2.2.jar;%APP_HOME%\lib\krangl-0.18.4.jar;%APP_HOME%\lib\plot-api-jvm-4.0.0.jar;%APP_HOME%\lib\ktor-client-core-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-websocket-serialization-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-serialization-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-events-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-websockets-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-http-cio-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-http-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-network-jvm-2.1.3.jar;%APP_HOME%\lib\ktor-utils-jvm-2.1.3.jar;%APP_HOME%\lib\koin-core-jvm-3.2.2.jar;%APP_HOME%\lib\lets-plot-common-2.4.0.jar;%APP_HOME%\lib\plot-config-portable-jvm-2.4.0.jar;%APP_HOME%\lib\plot-builder-portable-jvm-2.4.0.jar;%APP_HOME%\lib\plot-base-portable-jvm-2.4.0.jar;%APP_HOME%\lib\plot-common-portable-jvm-2.4.0.jar;%APP_HOME%\lib\vis-svg-portable-jvm-2.4.0.jar;%APP_HOME%\lib\base-portable-jvm-2.4.0.jar;%APP_HOME%\lib\ktor-io-jvm-2.1.3.jar;%APP_HOME%\lib\kotlinx-coroutines-jdk8-1.6.4.jar;%APP_HOME%\lib\exposed-core-0.40.1.jar;%APP_HOME%\lib\kotlinx-coroutines-core-jvm-1.6.4.jar;%APP_HOME%\lib\kotlinx-coroutines-slf4j-1.6.4.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.7.21.jar;%APP_HOME%\lib\logback-classic-1.2.11.jar;%APP_HOME%\lib\postgresql-42.3.7.jar;%APP_HOME%\lib\HikariCP-5.0.1.jar;%APP_HOME%\lib\jnanoid-2.0.0.jar;%APP_HOME%\lib\kotlin-statistics-1.2.1.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.7.21.jar;%APP_HOME%\lib\klaxon-5.6.jar;%APP_HOME%\lib\kotlin-reflect-1.7.20.jar;%APP_HOME%\lib\kotlin-logging-jvm-2.0.5.jar;%APP_HOME%\lib\kotlin-stdlib-1.7.21.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\config-1.4.2.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.7.21.jar;%APP_HOME%\lib\jansi-2.4.0.jar;%APP_HOME%\lib\gson-2.9.0.jar;%APP_HOME%\lib\netty-codec-http2-4.1.78.Final.jar;%APP_HOME%\lib\alpn-api-1.1.3.v20160715.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.78.Final.jar;%APP_HOME%\lib\logback-core-1.2.11.jar;%APP_HOME%\lib\checker-qual-3.5.0.jar;%APP_HOME%\lib\poi-ooxml-5.2.2.jar;%APP_HOME%\lib\poi-5.2.2.jar;%APP_HOME%\lib\commons-math3-3.6.1.jar;%APP_HOME%\lib\commons-csv-1.6.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\netty-codec-http-4.1.78.Final.jar;%APP_HOME%\lib\netty-handler-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-classes-kqueue-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-classes-epoll-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-4.1.78.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.78.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.78.Final.jar;%APP_HOME%\lib\netty-common-4.1.78.Final.jar;%APP_HOME%\lib\poi-ooxml-lite-5.2.2.jar;%APP_HOME%\lib\xmlbeans-5.0.3.jar;%APP_HOME%\lib\commons-compress-1.21.jar;%APP_HOME%\lib\commons-io-2.11.0.jar;%APP_HOME%\lib\curvesapi-1.07.jar;%APP_HOME%\lib\log4j-api-2.17.2.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\json-simple-1.1.1.jar;%APP_HOME%\lib\commons-codec-1.15.jar;%APP_HOME%\lib\SparseBitSet-1.2.jar


@rem Execute foodie-backend-v3
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %FOODIE_BACKEND_V3_OPTS%  -classpath "%CLASSPATH%" com.oreyo.ApplicationKt %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable FOODIE_BACKEND_V3_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%FOODIE_BACKEND_V3_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
