maxDownward is returning zero for negative, won't we miss larger sum due to this?

No.
Bcz we calculate from bottom up. When some subtree gives negative value, even though we find a huge
positive above that, taking this negative will only reduce the sum. Like in Kadane's algorithm.
You're looking for max sum. When you add someone, if they leave you in debt, skip him and entire
group came before him.