# Generational Garbage Collector

This project implements a Generational Garbage Collector in Java. The garbage collector uses a young and old generation to manage memory efficiently. It performs automatic garbage collection when the capacity of the young or old generation is reached and promotes objects from the young generation to the old generation.

## Features

- **Young and Old Generation**: The garbage collector uses two generations to manage memory.
- **Automatic Garbage Collection**: Mark and sweep phases happen automatically when the capacity is reached.
- **Promotion**: Objects are promoted from the young generation to the old generation after garbage collection.
- **Memory Management**: If the old generation does not have enough space after garbage collection, an `OutOfMemoryError` is thrown.

## Classes

### `GenerationalGarbageCollector`

This class manages the garbage collection process. It includes methods to add heap objects, add references, remove references, and perform garbage collection.

### `GarbageCollector`

This class is a basic garbage collector that uses a single heap and stack to manage memory. It includes methods to add heap objects, add stack objects, add references, remove references, and perform garbage collection.

## Usage

### Creating a Generational Garbage Collector

```java
GenerationalGarbageCollector gc = new GenerationalGarbageCollector(youngCapacity, oldCapacity);