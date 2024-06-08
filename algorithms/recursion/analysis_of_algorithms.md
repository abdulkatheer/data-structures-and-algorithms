## Subtracting functions

T(n) = T(n-1) + 1 --> O(n) </br>
T(n) = T(n-1) + n --> O(n<sup>2</sup>) </br>
T(n) = T(n-1) + logn --> O(n logn) </br>
T(n) = 2T(n-1) + 1 --> O(2<sup>n</sup>) </br>
T(n) = 3T(n-1) + 1 --> O(3<sup>n</sup>) </br>
T(n) = 3T(n-1) + n --> O(n 3<sup>n</sup>) </br>

T(n) = aT(n-b) + f(n) </br>
where a>0, b>0 </br>
f(n) = O(n<sup>k</sup>) where k >= 0 </br>

if a < 1, O(n<sup>k</sup>) or O(f(n)) </br>
if a=1, O(n<sup>k+1</sup>) or O(n * f(n)) </br>
if a > 1, O(n<sup>k</sup> * a<sup>n/b</sup>) or O(f(n) * a<sup>n/b</sup>) </br>

## Dividing functions

T(n) = T(n/2) + n --> O(n) </br>
T(n) = 2T(n/2) + n --> O(n logn) </br>

T(n) = a(T/b) + f(n) </br>
where a >= 1, b > 1 </br>
f(n) = O(n<sup>k</sup>log<sup>p</sup>n) </br>

1) log<sub>b</sub>a
2) k

case 1: if log<sub>b</sub>a > k --> O(n<sup>log<sub>b</sub>a</sup>) </br>
case 2: if log<sub>b</sub>a = k

1) p > -1 --> O(n<sup>k</sup> log<sup>p+1</sup>n)
2) p = -1 --> O(n<sup>k</sup> log logn)
3) p < -1 --> O(n<sup>k</sup>)

case 3: if log<sub>b</sub>a < k

1) p >= 0 --> O(n<sup>k</sup> log<sup>p</sup>n)
2) p < 0  --> O(n<sup>k</sup>)
