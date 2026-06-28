package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * {@code MyArrayList} is an array-based implementation of the {@link ListADT}
 * interface. It stores elements in a dynamically resizable array and provides
 * all standard list operations.
 *
 * <p>This implementation does not use any classes from {@code java.util.*}
 * collection framework (except {@code Arrays.copyOf} for resizing and
 * {@code NoSuchElementException} for the iterator).</p>
 *
 * @param <E> The type of elements held in this list.
 * @author Vansh
 * @version 1.0
 */
public class MyArrayList<E> implements ListADT<E>
{
    /** The default initial capacity of the internal array. */
    private static final int DEFAULT_CAPACITY = 10;

    /** The internal array that stores the list elements. */
    private Object[] data;

    /** The current number of elements stored in the list. */
    private int size;

    /**
     * Constructs an empty {@code MyArrayList} with the default initial capacity
     * of {@value #DEFAULT_CAPACITY}.
     */
    public MyArrayList()
    {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Returns the number of elements currently in the list.
     *
     * @return The current element count.
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Removes all elements from this list. After this call, the list will be
     * empty and the internal array is reset to the default capacity.
     */
    @Override
    public void clear()
    {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position and any subsequent elements
     * to the right.
     *
     * @param index The index at which the specified element is to be inserted.
     * @param toAdd The element to be inserted.
     * @return {@code true} if the element is added successfully.
     * @throws NullPointerException      If the specified element is {@code null}.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index > size()}).
     */
    @Override
    public boolean add( int index, E toAdd )
            throws NullPointerException, IndexOutOfBoundsException
    {
        if ( toAdd == null )
            throw new NullPointerException( "Cannot add null element to the list." );
        if ( index < 0 || index > size )
            throw new IndexOutOfBoundsException( "Index out of bounds: " + index );

        ensureCapacity();
        System.arraycopy( data, index, data, index + 1, size - index );
        data[index] = toAdd;
        size++;
        return true;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param toAdd The element to be appended.
     * @return {@code true} if the element is appended successfully.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    @Override
    public boolean add( E toAdd ) throws NullPointerException
    {
        if ( toAdd == null )
            throw new NullPointerException( "Cannot add null element to the list." );

        ensureCapacity();
        data[size++] = toAdd;
        return true;
    }

    /**
     * Appends all elements in the specified {@link ListADT} to the end of this
     * list, in the order returned by the specified list's iterator.
     *
     * @param toAdd The list whose elements are to be added.
     * @return {@code true} if the operation is successful.
     * @throws NullPointerException If the specified list is {@code null}.
     */
    @Override
    public boolean addAll( ListADT<? extends E> toAdd ) throws NullPointerException
    {
        if ( toAdd == null )
            throw new NullPointerException( "Cannot add a null list." );

        Iterator<? extends E> it = toAdd.iterator();
        while ( it.hasNext() )
        {
            add( it.next() );
        }
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index The index of the element to return.
     * @return The element at the specified position.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index >= size()}).
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public E get( int index ) throws IndexOutOfBoundsException
    {
        checkIndex( index );
        return (E) data[index];
    }

    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left.
     *
     * @param index The index of the element to remove.
     * @return The element that was removed.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index >= size()}).
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public E remove( int index ) throws IndexOutOfBoundsException
    {
        checkIndex( index );
        E removed = (E) data[index];
        System.arraycopy( data, index + 1, data, index, size - index - 1 );
        data[--size] = null;
        return removed;
    }

    /**
     * Removes the first occurrence of the specified element from this list.
     * If the element is not found, returns {@code null} and the list is
     * unchanged.
     *
     * @param toRemove The element to be removed.
     * @return The element that was removed, or {@code null} if not found.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public E remove( E toRemove ) throws NullPointerException
    {
        if ( toRemove == null )
            throw new NullPointerException( "Cannot remove a null element." );

        for ( int i = 0; i < size; i++ )
        {
            if ( toRemove.equals( data[i] ) )
            {
                return remove( i );
            }
        }
        return null;
    }

    /**
     * Replaces the element at the specified position with the specified element.
     *
     * @param index    The index of the element to replace.
     * @param toChange The element to be stored at the specified position.
     * @return The element previously at the specified position.
     * @throws NullPointerException      If the specified element is {@code null}.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index >= size()}).
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public E set( int index, E toChange )
            throws NullPointerException, IndexOutOfBoundsException
    {
        if ( toChange == null )
            throw new NullPointerException( "Cannot set a null element." );
        checkIndex( index );

        E old = (E) data[index];
        data[index] = toChange;
        return old;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if the list is empty; {@code false} otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param toFind The element whose presence is to be tested.
     * @return {@code true} if the list contains the element; {@code false} otherwise.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    @Override
    public boolean contains( E toFind ) throws NullPointerException
    {
        if ( toFind == null )
            throw new NullPointerException( "Cannot search for a null element." );

        for ( int i = 0; i < size; i++ )
        {
            if ( toFind.equals( data[i] ) )
                return true;
        }
        return false;
    }

    /**
     * Returns an array containing all elements in this list in proper sequence.
     * If the provided array is large enough, it is used; otherwise, a new array
     * of the same runtime type is allocated.
     *
     * @param toHold The array into which the elements of this list are to be
     *               stored.
     * @return An array containing the elements of this list.
     * @throws NullPointerException If the specified array is {@code null}.
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public E[] toArray( E[] toHold ) throws NullPointerException
    {
        if ( toHold == null )
            throw new NullPointerException( "The provided array cannot be null." );

        if ( toHold.length < size )
            toHold = Arrays.copyOf( toHold, size );

        System.arraycopy( data, 0, toHold, 0, size );
        return toHold;
    }

    /**
     * Returns an {@code Object} array containing all elements in this list in
     * proper sequence.
     *
     * @return An array containing all elements of this list in proper sequence.
     */
    @Override
    public Object[] toArray()
    {
        return Arrays.copyOf( data, size );
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * The iterator makes a snapshot copy of the list at the time it is created.
     *
     * @return An {@link Iterator} over the elements in this list.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new ArrayListIterator();
    }

    /**
     * Ensures the internal array has sufficient capacity to hold one more
     * element. If the array is full, it is doubled in size using
     * {@code Arrays.copyOf}.
     */
    private void ensureCapacity()
    {
        if ( size == data.length )
            data = Arrays.copyOf( data, data.length * 2 );
    }

    /**
     * Validates that the specified index is within the valid range for access
     * operations ({@code 0 <= index < size}).
     *
     * @param index The index to validate.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    private void checkIndex( int index ) throws IndexOutOfBoundsException
    {
        if ( index < 0 || index >= size )
            throw new IndexOutOfBoundsException( "Index out of bounds: " + index );
    }

    /**
     * Private inner class that implements the {@link Iterator} interface for
     * {@code MyArrayList}. Makes a deep copy of the current elements so the
     * iterator is not affected by future modifications to the list.
     */
    private class ArrayListIterator implements Iterator<E>
    {
        /** A snapshot copy of the list elements at the time the iterator was created. */
        private final Object[] snapshot;

        /** The current position of the iterator cursor. */
        private int cursor;

        /**
         * Constructs an {@code ArrayListIterator} by taking a snapshot of the
         * current list contents.
         */
        @SuppressWarnings( "unchecked" )
        public ArrayListIterator()
        {
            snapshot = Arrays.copyOf( data, size );
            cursor = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         *
         * @return {@code true} if there are more elements to iterate over.
         */
        @Override
        public boolean hasNext()
        {
            return cursor < snapshot.length;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return The next element.
         * @throws NoSuchElementException If the iteration has no more elements.
         */
        @SuppressWarnings( "unchecked" )
        @Override
        public E next() throws NoSuchElementException
        {
            if ( !hasNext() )
                throw new NoSuchElementException( "No more elements in the iterator." );
            return (E) snapshot[cursor++];
        }
    }
}