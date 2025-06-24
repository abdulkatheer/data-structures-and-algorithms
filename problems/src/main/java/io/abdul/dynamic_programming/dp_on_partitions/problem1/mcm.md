## Matrix multiplication

A11 A12
A21 A21
X
B11 B12
B21 B22
=
D11 D12
D21 D22

where,
D11 = A11*B11 + A12*B21
D12 = A11*B12 + A12*B22
D21 = A21*B11 + A22*B21
D22 = A21*B12 + A22*B22

1) We can multiple any two matrices only when length of column of first and row of second should be same
   AxB X BxC
2) Matrix multiplication has associative property. Meaning grouping doesn't matter.
   A X B X C = (A X B) X c = A X (B X C)
3) Doesn't have commutative property. Meaning order matters.
   A X B != B X A
4) Operations required:
   AxB X BxC = A X B X C
5) Result of multiplication: 
   AxB X BxC = AxC

Ex: 10, 10, 10, 10
10x10 * 10x10 * 10*10
A * B * C
A * (B*C) = 10x10 * (10x10 * 10x10)
10x10 = 0 ops
10x10 * 10x10 = 10 * 10 * 10 1000 ops
10x10 * (10x10 * 10x10) = 10x10 * 10x10 = 10 * 10 * 10 = 1000 ops

So 0 + 1000 + 1000 = 2000 ops