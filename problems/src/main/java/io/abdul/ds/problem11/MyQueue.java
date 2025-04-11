package io.abdul.ds.problem11;

import java.util.Stack;

/*
Let's use 1st stack for reads only (pull), and 2nd stack for writes only (push). Let's for simplicity call them pushstackpush stackpushstack and pullstackpull stackpullstack.
At each push() queue operation we just put element x to the pushpushpush stack.
When we need to pop() or peek() element, we do the following:

if pullstack is not empty - pop() from it;
otherwise - pop all elements from pushstack to pullstack.
Let's see what whill happen:
pullstack : []
pushstack : [1, 2, 3, 4]

When pop or peek is called
1)
pullstack : [1]
pushstack : [2, 3, 4]
2)
pullstack : [2, 1]
pushstack : [3, 4]
3)
pullstack : [3, 2, 1]
pushstack : [4]
3)
pullstack : [4, 3, 2, 1]
pushstack : []

Now pullstack plays role of real queue - as elements 1,2,3,4 are in inverted order now (4,3,2,1)! So going forward we'll be able just to pop elements 1 by 1.

This movement (adjustment) operation takes up to O(n) time, but for the next up to O(n) elements (depending on how much elements used to be in the pushstack) we'll have O(1) complexity.
In total we'll have 1 O(n) and (n-1) O(1) operations, that makes O(n) complexity for n operations in total - that is exactly amortized O(1)  per operation.

We can do this adjustment at each operation - but it's important to check whether pullstack is empty - as otherwise order will be violated! (Exercise: check why it will happen).

Complexity
Time complexity: amortized O(1) for each operation
Space complexity: O(n)
 */
// #tag_ds
class MyQueue {
    private final Stack<Integer> pullStack = new Stack<>();
    private final Stack<Integer> pushStack = new Stack<>();

    public MyQueue() {

    }

    public void push(int x) {
        pushStack.push(x);
    }

    public int pop() {
        invert();
        return pullStack.pop();
    }

    public int peek() {
        invert();
        return pullStack.peek();
    }

    public boolean empty() {
        return pullStack.isEmpty() && pushStack.isEmpty();
    }

    private void invert() {
        if (pullStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                pullStack.push(pushStack.pop());
            }
        }
    }
}