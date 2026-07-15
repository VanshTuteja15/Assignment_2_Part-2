package implementations;

import utilities.Iterator;
import utilities.ListADT;

import java.util.NoSuchElementException;

/**
 * {@code MyDLL} is a doubly linked list implementation of the {@link ListADT}
 * interface. Each element is stored inside a {@link MyDLLNode}, and the list
 * maintains references to both the head and tail nodes so that insertions at
 * either end are O(1).
 *
 * <p>This implementation does not use any classes from the {@code java.util.*}
 * collection framework, other than {@code NoSuchElementException} which is
 * used by the iterator.</p>
 *
 * @param <E> The type of elements held in this list.
 * @author Vansh
 * @version 1.0
 */
public class MyDLL<E> implements ListADT<E>
{
    /** Reference to the first node in the list, or {@code null} if the list is empty. */
    private MyDLLNode<E> head;

    /** Reference to the last node in the list, or {@code null} if the list is empty. */
    private MyDLLNode<E> tail;

    /** The current number of elements stored in the list. */
    private int size;

    /**
     * Constructs an empty {@code MyDLL}.
     */
    public MyDLL()
    {
        head = null;
        tail = null;
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
     * Removes all elements from this list.
     */
    @Override
    public void clear()
    {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right.
     *
     * @param index The index at which the specified element is to be inserted.
     * @param toAdd The element to be inserted.
     * @return {@code true} if the element is added successfully.
     * @throws NullPointerException      If the specified element is {@code null}.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index > size()}).
     */
    @Override
    public boolean add( int index, E toAdd ) throws NullPointerException, IndexOutOfBoundsException
    {
        if ( toAdd == null )
            throw new NullPointerException( "Cannot add null element to the list." );
        if ( index < 0 || index > size )
            throw new IndexOutOfBoundsException( "Index out of bounds: " + index );

        MyDLLNode<E> newNode = new MyDLLNode<>( toAdd );

        if ( size == 0 )
        {
            head = newNode;
            tail = newNode;
        }
        else if ( index == 0 )
        {
            newNode.setNext( head );
            head.setPrev( newNode );
            head = newNode;
        }
        else if ( index == size )
        {
            newNode.setPrev( tail );
            tail.setNext( newNode );
            tail = newNode;
        }
        else
        {
            MyDLLNode<E> current = getNode( index );
            MyDLLNode<E> before = current.getPrev();

            newNode.setPrev( before );
            newNode.setNext( current );
            before.setNext( newNode );
            current.setPrev( newNode );
        }

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
        return add( size, toAdd );
    }

    /**
     * Appends all elements in the specified {@link ListADT} to the end of
     * this list, in the order returned by the specified list's iterator.
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
    @Override
    public E get( int index ) throws IndexOutOfBoundsException
    {
        checkIndex( index );
        return getNode( index ).getElement();
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index The index of the element to remove.
     * @return The element that was removed.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index >= size()}).
     */
    @Override
    public E remove( int index ) throws IndexOutOfBoundsException
    {
        checkIndex( index );
        MyDLLNode<E> target = getNode( index );
        unlink( target );
        return target.getElement();
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
    @Override
    public E remove( E toRemove ) throws NullPointerException
    {
        if ( toRemove == null )
            throw new NullPointerException( "Cannot remove a null element." );

        MyDLLNode<E> current = head;
        while ( current != null )
        {
            if ( toRemove.equals( current.getElement() ) )
            {
                unlink( current );
                return current.getElement();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Replaces the element at the specified position with the specified
     * element.
     *
     * @param index    The index of the element to replace.
     * @param toChange The element to be stored at the specified position.
     * @return The element previously at the specified position.
     * @throws NullPointerException      If the specified element is {@code null}.
     * @throws IndexOutOfBoundsException If the index is out of range
     *                                   ({@code index < 0 || index >= size()}).
     */
    @Override
    public E set( int index, E toChange ) throws NullPointerException, IndexOutOfBoundsException
    {
        if ( toChange == null )
            throw new NullPointerException( "Cannot set a null element." );
        checkIndex( index );

        MyDLLNode<E> node = getNode( index );
        E old = node.getElement();
        node.setElement( toChange );
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

        MyDLLNode<E> current = head;
        while ( current != null )
        {
            if ( toFind.equals( current.getElement() ) )
                return true;
            current = current.getNext();
        }
        return false;
    }

    /**
     * Returns an array containing all elements in this list in proper
     * sequence. If the provided array is large enough, it is used;
     * otherwise, a new array of the same runtime type is allocated.
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
            toHold = (E[]) java.lang.reflect.Array.newInstance( toHold.getClass().getComponentType(), size );

        int i = 0;
        MyDLLNode<E> current = head;
        while ( current != null )
        {
            toHold[i++] = current.getElement();
            current = current.getNext();
        }
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
        Object[] result = new Object[size];
        int i = 0;
        MyDLLNode<E> current = head;
        while ( current != null )
        {
            result[i++] = current.getElement();
            current = current.getNext();
        }
        return result;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * The iterator makes a snapshot copy of the list at the time it is
     * created.
     *
     * @return An {@link Iterator} over the elements in this list.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new DLLIterator();
    }

    /**
     * Returns the node located at the specified index. Traverses from the
     * head or the tail, whichever is closer, to minimize the number of hops.
     *
     * @param index The index of the node to retrieve; assumed to be valid.
     * @return The node at the specified index.
     */
    private MyDLLNode<E> getNode( int index )
    {
        MyDLLNode<E> current;
        if ( index <= size / 2 )
        {
            current = head;
            for ( int i = 0; i < index; i++ )
                current = current.getNext();
        }
        else
        {
            current = tail;
            for ( int i = size - 1; i > index; i-- )
                current = current.getPrev();
        }
        return current;
    }

    /**
     * Removes the specified node from the list, re-linking its neighbours,
     * and decrements the size.
     *
     * @param node The node to unlink; assumed to be a member of this list.
     */
    private void unlink( MyDLLNode<E> node )
    {
        MyDLLNode<E> before = node.getPrev();
        MyDLLNode<E> after = node.getNext();

        if ( before == null )
            head = after;
        else
            before.setNext( after );

        if ( after == null )
            tail = before;
        else
            after.setPrev( before );

        node.setPrev( null );
        node.setNext( null );
        size--;
    }

    /**
     * Validates that the specified index is within the valid range for
     * access operations ({@code 0 <= index < size}).
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
     * {@code MyDLL}. Makes a deep copy of the current elements so the
     * iterator is not affected by future modifications to the list.
     */
    private class DLLIterator implements Iterator<E>
    {
        /** A snapshot copy of the list elements at the time the iterator was created. */
        private final Object[] snapshot;

        /** The current position of the iterator cursor. */
        private int cursor;

        /**
         * Constructs a {@code DLLIterator} by taking a snapshot of the
         * current list contents.
         */
        public DLLIterator()
        {
            snapshot = toArray();
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
