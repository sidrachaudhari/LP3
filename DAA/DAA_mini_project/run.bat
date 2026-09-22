@echo off
REM ====================================================================
REM SPPU 2019 Pattern - BE Computer Engineering - LP3 (DAA)
REM Build and Run Script for Merge Sort vs Multithreaded Merge Sort
REM ====================================================================

echo [1/2] Compiling Java source files...
if not exist "bin" mkdir "bin"

javac -d bin src/sppu/lp3/mergesort/*.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed! Please check your Java installation.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Compilation successful. Launching application...
echo.
java -cp bin sppu.lp3.mergesort.Main

pause
