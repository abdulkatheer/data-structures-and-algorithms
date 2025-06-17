Great — let’s dive deep into why this **Dynamic Programming** solution for **Best Time to Buy and Sell Stock III**
works.

---

## 🧠 Problem Recap

You’re given:

* An array of prices `arr[]`, where `arr[i]` = stock price on day `i`
* You can complete **at most two transactions**
* **Only one stock can be held at a time** (i.e., must sell before buying again)
* A transaction = one buy + one sell

**Goal**: Maximize profit by deciding when to buy/sell, while satisfying the above.

---

## ✅ Key Observations

1. Each transaction is a **buy followed by a sell**
2. At any day, you can either:

    * **Do nothing**
    * **Buy** (if not holding)
    * **Sell** (if holding)
3. The choice you make **depends on past decisions**, and what remains (how many transactions you still have)

---

## 🎯 Why Dynamic Programming?

We use **DP** because:

* The problem has **overlapping subproblems** (e.g., max profit from day 4 onward with 1 transaction left is reused)
* It has an **optimal substructure**: The best decision on a day depends only on decisions of previous days
* We explore all choices and **remember** the best using a table

---

## 🔍 State Definition

We define the state as:

```text
dp[day][transactions_left][holding]
```

Where:

* `day` = current day (0 to n−1)
* `transactions_left` = how many full transactions (buy+sell) we can still do
* `holding` = 1 if currently holding a stock, 0 if not

---

## 🔁 Transitions

### Case 1: Not Holding (`holding = 0`)

We can either:

* **Do nothing** → same as yesterday: `dp[day - 1][t][0]`
* **Sell today** → if we were holding yesterday: `dp[day - 1][t][1] + price[day]`

So:

```java
dp[day][t][0]=

max(dp[day-1][t][0], dp[day-1][t][1]+prices[day]);
```

---

### Case 2: Holding (`holding = 1`)

We can either:

* **Do nothing** → continue holding: `dp[day - 1][t][1]`
* **Buy today** → we use one of the remaining transactions: `dp[day - 1][t - 1][0] - prices[day]`

So:

```java
dp[day][t][1]=

max(dp[day-1][t][1], dp[day-1][t-1][0]-prices[day]);
```

Notice: we subtract price because we’re spending money when we buy.

---

## 🔁 Initialization

On `day = 0`:

* If we're not holding → profit is 0
* If we're holding → we must have bought on day 0

```java
for(int t = 0;
t <=2;t++){
dp[0][t][0]=0;
dp[0][t][1]=-prices[0];  // buying on day 0
        }
```

---

## 🧮 Final Answer

We're interested in:

* Last day (`n-1`)
* At most 2 transactions used (`2`)
* **Not holding** stock (we must sell before profit is realized)

```java
return dp[n-1][2][0];
```

---

## 🧩 Example Walkthrough: `[4, 2, 7, 1, 11, 5]`

### Idea:

* Buy on day 1 (price 2), sell on day 2 (price 7) → profit = 5
* Buy on day 3 (price 1), sell on day 4 (price 11) → profit = 10
* Total = 5 + 10 = 15

DP records:

* At each step, what’s the best profit for 0/1/2 transactions and holding/not-holding state
* Helps us avoid redundant work

---

## ✅ Why It Works

* It **explores all possibilities**, by simulating every valid sequence of buy/sell up to 2 transactions
* It uses **memory (memoization)** to avoid repeating the same work
* It uses **inductive reasoning**: optimal solution at day `i` builds from optimal solution at day `i-1`

---

## 📦 Time & Space

* **Time**: `O(n * 3 * 2)` → `O(n)` where `n` is number of days
* **Space**: `O(n * 3 * 2)` → can be optimized to `O(3 * 2)` using 2 rows only (rolling DP)

---

Would you like:

* A space-optimized version?
* Extension to K transactions?
* How to convert to bottom-up table manually?

Let me know!
