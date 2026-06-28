package implementations;

import utilities.Iterator;
import utilities.StackADT;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 * {@code MyStack} is an array-list-based implementation of the {@link StackADT}
 * interface. It follows the Last-In-First-Out (LIFO) principle and uses
 * {@link MyArrayList} as its underlying data structure.
 *
 * <p>The top of the stack corresponds to the last index of the internal
 * {@link MyArrayList}. This allows O(1) push and pop operations.</p>
 *
 * <p>This implementation does not use any classes from the {@code java.util.*}
 * collection framework (except {@code EmptyStackException} and
 * {@code NoSuchElementException} which are standard Java exceptions).</p>
 *
 * @param <E> The type of elements held in this stack.
 * @author Vansh
 * @version 2.0
 */
public class MyStack<E> implements StackADT<E>
{
    /**
     * The underlying {@link MyArrayList} used to store stack elements.
     * Index {@code 0} is the bottom of the stack; index {@code size - 1}
     * is the top of the stack.
     */
    private MyArrayList<E> list;

    /**
     * Constructs an empty {@code MyStack}.
     */
    public MyStack()
    {
        list = new MyArrayList<>();
    }

    /**
     * Pushes the specified element onto the top of this stack.
     *
     * <p>Pre-condition: {@code toAdd} must not be {@code null}.</p>
     * <p>Post-condition: The element is placed on top of the stack and
     * the stack size increases by one.</p>
     *
     * @param toAdd The element to push onto the stack.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    @Override
    public void push( E toAdd ) throws NullPointerException
    {
        if ( toAdd == null )
            throw new NullPointerException( "Cannot push a null element onto the stack." );
        list.add( toAdd );
    }

    /**
     * Removes and returns the element at the top of this stack.
     *
     * <p>Pre-condition: The stack must not be empty.</p>
     * <p>Post-condition: The top element is removed and the stack size
     * decreases by one.</p>
     *
     * @return The element removed from the top of the stack.
     * @throws EmptyStackException If the stack is empty.
     */
    @Override
    public E pop() throws EmptyStackException
    {
        if ( isEmpty() )
            throw new EmptyStackException();
        return list.remove( list.size() - 1 );
    }

    /**
     * Returns the element at the top of this stack without removing it.
     *
     * <p>Pre-condition: The stack must not be empty.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return The element at the top of the stack.
     * @throws EmptyStackException If the stack is empty.
     */
    @Override
    public E peek() throws EmptyStackException
    {
        if ( isEmpty() )
            throw new EmptyStackException();
        return list.get( list.size() - 1 );
    }

    /**
     * Removes all elements from this stack.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack is empty and its size is zero.</p>
     */
    @Override
    public void clear()
    {
        list.clear();
    }

    /**
     * Returns {@code true} if this stack contains no elements.
     *
     * @return {@code true} if the stack is empty; {@code false} otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    /**
     * Returns an {@code Object} array containing all elements in this stack
     * in order from <b>top to bottom</b>.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return An array containing all elements of the stack from top to bottom.
     */
    @Override
    public Object[] toArray()
    {
        int sz = list.size();
        Object[] result = new Object[sz];
        for ( int i = 0; i < sz; i++ )
        {
            result[i] = list.get( sz - 1 - i );
        }
        return result;
    }

    /**
     * Returns an array containing all elements in this stack in order from
     * <b>top to bottom</b>. If the given array is large enough it is used;
     * otherwise a new array of the same runtime type is allocated.
     *
     * <p>Pre-condition: The specified array must not be {@code null}.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @param toHold The array into which the elements of this stack are to be
     *               stored, if it is big enough.
     * @return An array containing all stack elements from top to bottom.
     * @throws NullPointerException If the specified array is {@code null}.
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public E[] toArray( E[] toHold ) throws NullPointerException
    {
        if ( toHold == null )
            throw new NullPointerException( "The provided array cannot be null." );

        int sz = list.size();
        if ( toHold.length < sz )
            toHold = Arrays.copyOf( toHold, sz );

        for ( int i = 0; i < sz; i++ )
        {
            toHold[i] = list.get( sz - 1 - i );
        }
        return toHold;
    }

    /**
     * Returns {@code true} if this stack contains the specified element.
     *
     * <p>Pre-condition: {@code toFind} must not be {@code null}.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @param toFind The element to search for.
     * @return {@code true} if the element is found; {@code false} otherwise.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    @Override
    public boolean contains( E toFind ) throws NullPointerException
    {
        if ( toFind == null )
            throw new NullPointerException( "Cannot search for a null element." );
        return list.contains( toFind );
    }

    /**
     * Returns the 1-based position from the top of the stack where the
     * specified element is located. The topmost element is at position 1.
     * Returns {@code -1} if the element is not found.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @param toFind The element to search for.
     * @return The 1-based position from the top, or {@code -1} if not found.
     */
    @Override
    public int search( E toFind )
    {
        if ( toFind == null )
            return -1;

        for ( int i = list.size() - 1; i >= 0; i-- )
        {
            if ( toFind.equals( list.get( i ) ) )
                return list.size() - i;
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in this stack, from
     * <b>top to bottom</b>.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The stack remains unchanged.</p>
     *
     * @return An {@link Iterator} over the stack elements from top to bottom.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new StackIterator();
    }

    /**
     * Compares the specified {@link StackADT} with this stack for equality.
     * Two stacks are equal if they have the same size and all corresponding
     * elements are equal when compared top to bottom.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: Both stacks remain unchanged.</p>
     *
     * @param that The stack to compare with this stack.
     * @return {@code true} if both stacks contain the same elements in the
     *         same order; {@code false} otherwise.
     */
    @Override
    public boolean equals( StackADT<E> that )
    {
        if ( that == null )
            return false;
        if ( this.size() != that.size() )
            return false;

        Iterator<E> thisIt = this.iterator();
        Iterator<E> thatIt = that.iterator();

        while ( thisIt.hasNext() )
        {
            if ( !thisIt.next().equals( thatIt.next() ) )
                return false;
        }
        return true;
    }

    /**
     * Returns the number of elements currently in this stack.
     *
     * @return The number of elements in the stack.
     */
    @Override
    public int size()
    {
        return list.size();
    }

    /**
     * Returns {@code true} if the stack is at capacity. Since this is a
     * dynamically sized stack with no fixed upper bound, this method always
     * returns {@code false}.
     *
     * @return {@code false} always, as this stack has no fixed capacity.
     */
    @Override
    public boolean stackOverflow()
    {
        return false;
    }

    // -----------------------------------------------------------------------
    // Private inner class: StackIterator
    // -----------------------------------------------------------------------

    /**
     * Private inner class that implements the {@link Iterator} interface for
     * {@code MyStack}. Iterates from the top of the stack to the bottom.
     */
    private class StackIterator implements Iterator<E>
    {
        /**
         * The current cursor position, starting at the top of the stack
         * (i.e., {@code list.size() - 1}) and moving toward index 0.
         */
        private int cursor;

        /**
         * Constructs a {@code StackIterator} that starts at the top of
         * the stack.
         */
        public StackIterator()
        {
            cursor = list.size() - 1;
        }

        /**
         * Returns {@code true} if there are more elements to iterate over.
         *
         * @return {@code true} if the iterator has more elements.
         */
        @Override
        public boolean hasNext()
        {
            return cursor >= 0;
        }

        /**
         * Returns the next element in the iteration, moving from top to bottom.
         *
         * @return The next element.
         * @throws NoSuchElementException If the iteration has no more elements.
         */
        @Override
        public E next() throws NoSuchElementException
        {
            if ( !hasNext() )
                throw new NoSuchElementException( "No more elements in the stack iterator." );
            return list.get( cursor-- );
        }
    }
}