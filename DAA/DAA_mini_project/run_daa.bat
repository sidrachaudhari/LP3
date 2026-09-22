@echo off
REM ====================================================================
REM SPPU 2019 Pattern - BE Computer Engineering - LP3 (DAA)
REM Build and Run Script for ALL DAA Assignments (1 to 5)
REM ====================================================================

echo ============================================================
echo   SPPU 2019 Pattern - BE Computer Engineering - LP3 (DAA)
echo   Build and Run Script for All DAA Assignments
echo ============================================================
echo.

echo [1/2] Compiling all Java source files...
if not exist "bin" mkdir "bin"

javac -d bin src/sppu/lp3/daa1/DAA_1.java src/sppu/lp3/daa2/DAA_2.java src/sppu/lp3/daa3/DAA_3.java src/sppu/lp3/daa4/DAA_4.java src/sppu/lp3/daa5/DAA_5.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed! Please check your Java installation.
    pause
    exit /b %ERRORLEVEL%
)

echo [2/2] Compilation successful!
echo.
echo Select which assignment to run:
echo  1. DAA_1 - Fibonacci (Recursive ^& Non-Recursive)
echo  2. DAA_2 - Huffman Encoding (Greedy)
echo  3. DAA_3 - Fractional Knapsack (Greedy)
echo  4. DAA_4 - 0-1 Knapsack (DP ^& Branch and Bound)
echo  5. DAA_5 - N-Queens (Backtracking)
echo  0. Exit
echo.

set /p choice="Enter your choice (0-5): "

if "%choice%"=="1" java -cp bin sppu.lp3.daa1.DAA_1
if "%choice%"=="2" java -cp bin sppu.lp3.daa2.DAA_2
if "%choice%"=="3" java -cp bin sppu.lp3.daa3.DAA_3
if "%choice%"=="4" java -cp bin sppu.lp3.daa4.DAA_4
if "%choice%"=="5" java -cp bin sppu.lp3.daa5.DAA_5
if "%choice%"=="0" exit /b

pause
