# Introduction

## What is Data Structure?

### Data

Files, images, texts and any digital form of information is called Data

### Data Structure

Arrangement of data in the main memory
Tailored for efficient read/write for a particular problem/usecase/program

### Database

Arrangement of data in permanent storage such as HDD, SSD
Large data that can't fit in main memory
Special software to read/write the data
Ex: RDBMS

### Data warehouse

Arrangement of data in array of physical storages and servers
Legacy, archived data which is not operational
Used for analytical, auditing etc.,

## Stack vs Heap memory

- Program exists in an executable format (binary, jar, war etc.,) in the permanent storage (HDD)
- When a program is run, it'll be loaded into Main memory (RAM)
- During the runtime of program, it creates and uses data. That data is stored in stack and heap space.
- Each program starts with a function of code and that may trigger chain of functions throughout the lifecycle of the
  program
- Each function call pushes a stackframe in the stack
- Once a function execution is completed, stackframe will be popped off of stack
- Variables in the function like int,float or allocated in the corresponding function's stackframe statically when the
  stackframe is created. Because size of the stack will be known in the compile-time itself. So it can be created ahead
  of function execution.
- Hence, this is called Static Memory Allocation. These memory allocations will be wiped along with the stackframe.
- Variables like pointers can be created (using malloc in C, new in C++/Java/C#) which will hold a memory address in the
  main memory.
- Address is stored in the Stack like any other variable, but the memory at the address is allocated in the heap.
- Let's say ```int *p = new int[5];```. Here *p is an integer pointer and will take 2 bytes in the stack. new int[5] is
  an array of size 5, will take 10 bytes in the heap.
- Data in heap has to be cleaned up manually (in C & C++, whereas Java,C# has Garbage Collectors for this).
- Memory allocated in heap is happening during the runtime and on demand (like when program is executed). Size of memory
  will be known only duirng runtime of the program.
- Hence, this is called Dynamic Memory Allocation.

## Categories of Data Structures

1. Physical Data Structure
    - Data arrangement in the main memory
    - Arrays
        - Contiguous memory locations in main memory
        - Bounded / fixed size
        - Constant time lookup as we can calculate memory location of any item in the array
    - Linked Lists
        - Each element is stored in random location in the main memory
        - We only store first elements address and each element stores next elements address (Singly-linked list)
        - Last element will point to null
        - Here even though data is spread across main memory, each element carries address of their neighbour and
          thereby forming a link to entire dataset
        - O(n) time lookup as we always to start searching for an element from the head
2. Logical Data Structure
    - Build using one of or combination of Physical Data Structures for certain use-cases and problems
    - Doesn't change how memory is organized in main memory. Just uses logic to expose different read/write patterns
    - Types
        - Linear
            - Stack - LIFO
            - Queue - FIFO
        - Non-linear
            - Tree
            - Graph
        - Tabular
            - Hashtable

## Data Type

- Data Type defines representation of data in memory and operations allowed on the data

### Primitive Data Type

- Defined in the language / OS / Hardware
- Integer
    - Representation
        - 4 bytes, contiguous memory
        - 1 sign bit
        - 31 data bits
        - Can store -2^31 to 2^31-1
    - Operations
        - Arithmetic (+ - * / %)
        - ++ --
        - Logical
        - etc.,

### Abstract Data Type

- Custom Data Types
- Built using Primitive Data Types
- List
    - Representation
        - Storage space - Array or Linked List
        - Capacity
        - Size
    - Operations
        - Add(element)
        - Add(index, element)
        - Remove(index)
        - Set(index, Element)
        - Get(index)
        - etc.,
- Not all classes are ADT. If the class is meant to store data and allow us to perform operations on it, then it's an
  ADT.
- Ex: Stack, Queue
