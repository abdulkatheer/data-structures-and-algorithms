When we start from day 0, why do we reduce/increase transaction left on sell?
If we reduce on buy, let's say we're left with 1 and we buy it become zero.
The base case which checks transactionLeft will execute before selling it. So reduce on sell, for guaranteed sell.