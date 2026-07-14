package utilities;

import java.util.EmptyStackException;

/**
 * The {@code StackADT} interface defines the abstract data type for a
 * stack data structure to be used in CPRG304 at SAIT Polytechnic.
 *
 * <p>A stack follows the Last-In-First-Out (LIFO) principle, meaning the
 * last element pushed onto the stack is the first one to be popped off.
 * This interface provides all standard stack operations as well as several
 * helper methods for additional flexibility.</p>
 *
 * <p>Implementations of this interface must not use any classes from
 * {@code java.util.*} collection framework as the underlying data
 * structure.</p>
 *
 * @param <E> The type of elements held in this stack.
 * @author Vansh
 * @version 1.0
 */
public interface StackADT<E>
{
    /**
     * Pushes the specified element onto the top of this stack.
     *
     * <p>Pre-condition: {@code toAdd} must not be {@code null}.</p>
     * <p>Post-condition: The element is placed on top of the stack and
     * the stack size increases by one.</p>
     *
     * @param toAdd The element to be pushed onto the top of the stack.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    public void push( E toAdd ) throws NullPointerException;

    /**
     * Removes and returns the element at the top of this stack.
     *
     * <p>Pre-condition: The stack must not be empty.</p>
     * <p>Post-condition: The top element is removed and the stack size
     * decreases by one.</p>
     *
     * @return The element removed from the top of the stack.
     * @throws EmptyStackException If the stack contains no elements.
     */
    public E pop() throws EmptyStackException;

    /**
     * Returns the element at the top of this stack without removing it.
     *
     * <p>Pre-condition: The stack must not be empty.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return The element at the top of the stack.
     * @throws EmptyStackException If the stack contains no elements.
     */
    public E peek() throws EmptyStackException;

    /**
     * Removes all elements from this stack.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack is empty and its size is zero.</p>
     */
    public void clear();

    /**
     * Returns {@code true} if this stack co    ntains no elements.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return {@code true} if the stack is empty; {@code false} otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns an {@code Object} array containing all elements in this stack
     * in proper sequence from bottom to top.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return An array containing all elements of this stack in proper sequence.
     */
    public Object[] toArray();

    /**
     * Returns an array containing all elements in this stack in proper
     * sequence. The runtime type of the returned array is that of the
     * specified array. If the stack fits in the specified array, it is
     * returned therein; otherwise, a new array of the same runtime type
     * is allocated.
     *
     * <p>Pre-condition: The specified array must not be {@code null}.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @param toHold The array into which the elements of this stack are to
     *               be stored, if it is big enough; otherwise, a new array
     *               of the same runtime type is allocated for this purpose.
     * @return An array containing the elements of this stack.
     * @throws NullPointerException If the specified array is {@code null}.
     */
    public E[] toArray( E[] toHold ) throws NullPointerException;

    /**
     * Returns {@code true} if this stack contains the specified element.
     * More formally, returns {@code true} if and only if this stack contains
     * at least one element {@code e} such that {@code toFind.equals(e)}.
     *
     * <p>Pre-condition: {@code toFind} must not be {@code null}.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @param toFind The element whose presence in this stack is to be tested.
     * @return {@code true} if this stack contains the specified element;
     *         {@code false} otherwise.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    public boolean contains( E toFind ) throws NullPointerException;

    /**
     * Returns the 1-based position from the top of the stack where the
     * specified element is located. The topmost element on the stack is
     * considered to be at distance 1. The {@code equals} method is used
     * to compare {@code toFind} to the elements in this stack.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @param toFind The element to search for in this stack.
     * @return The 1-based position from the top of the stack where the
     *         element is located, or {@code -1} if the element is not
     *         on the stack.
     */
    public int search( E toFind );

    /**
     * Returns an iterator over the elements in this stack in proper sequence,
     * from top to bottom.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return An {@link Iterator} over the elements in this stack from
     *         top to bottom.
     */
    public Iterator<E> iterator();

    /**
     * Compares the specified {@link StackADT} with this stack for equality.
     * Two stacks are considered equal if they contain equal elements
     * appearing in the same order from top to bottom.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: Both stacks remain unchanged.</p>
     *
     * @param that The {@code StackADT} to be compared to this stack.
     * @return {@code true} if the two stacks are equal; {@code false} otherwise.
     */
    public boolean equals( StackADT<E> that );

    /**
     * Returns the number of elements currently in this stack.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return The current number of elements in the stack as an integer.
     */
    public int size();

    /**
     * Returns {@code true} if the number of elements in the stack equals
     * its fixed maximum capacity. This method is only meaningful when a
     * fixed-size stack is required; dynamically sized implementations
     * should always return {@code false}.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     * 	
     * @return {@code true} if the stack is at capacity; {@code false} otherwise.
     */
    public boolean stackOverflow();
}