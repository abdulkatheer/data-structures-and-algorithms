https://www.naukri.com/code360/library/kadanes-algorithm

[Read simple analogy here](../../practice/problem5/Kadane's%20Algorithm.txt)

Ex 1: -6 -5 -4 -1 -2 -3 -6

| Selected | Wallet | Maximum recorded |
|----------|--------|------------------|
|          | 0      | -Infinity        |
| -6       | -6     | -6               |
| -5       | -5     | -5               |
| -4       | -4     | -4               |
| -1       | -1     | -1               |
| -2       | -2     | -1               |
| -3       | -3     | -1               |
| -6       | -6     | -1               |               

Result = -1

Ex 2: -1 5 -2 1 -10 0 -2 0 18 -1

| Selected   | Wallet | Maximum recorded |
|------------|--------|------------------|
|            | 0      | -Infinity        |
| -1         | -1     | -1               |
| 5          | 5      | 5                |
| 5 -2       | 3      | 5                |
| 5 -2 1     | 4      | 5                |
| 5 -2 1 -10 | -6     | 5                |
| 0          | 0      | 5                |
| 0 -2       | -2     | 5                |
| 0          | 0      | 5                |
| 0 18       | 18     | 18               |
| 0 18 -1    | 17     | 18               |

