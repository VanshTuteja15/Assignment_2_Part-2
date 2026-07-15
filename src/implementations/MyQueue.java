package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

import java.util.NoSuchElementException;

/**
 * {@code MyQueue} is a {@link MyDLL}-based implementation of the
 * {@link QueueADT} interface. It follows the First-In-First-Out (FIFO)
 * principle: elements are enqueued at the tail of the underlying list and
 * dequeued from the head.
 *
 * <p>This implementation does not use any classes from the {@code java.util.*}
 * collection framework, other than {@code NoSuchElementException} which is a
 * standard Java exception used by the iterator.</p>
 *
 * @param <E> The type of elements held in this queue.
 * @author Vansh
 * @version 1.0
 */
public class MyQueue<E> implements QueueADT<E>
{
    /**
     * The underlying {@link MyDLL} used to store queue elements. Index
     * {@code 0} (the head of the list) is the front of the queue; the tail
     * of the list is the back of the queue.
     */
    private MyDLL<E> list;

    /**
     * Constructs an empty {@code MyQueue}.
     */
    public MyQueue()
    {
        list = new MyDLL<>();
    }

    /**
     * Places the specified element at the back of this queue.
     *
     * <p>Pre-condition: {@code toAdd} must not be {@code null}.</p>
     * <p>Post-condition: The element is appended to the back of the queue
     * and the queue size increases by one.</p>
     *
     * @param toAdd The item to be added to the queue.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    @Override
    public void enqueue( E toAdd ) throws NullPointerException
    {
        if ( toAdd == null )
            throw new NullPointerException( "Cannot enqueue a null element." );
        list.add( toAdd );
    }

    /**
     * Removes and returns the element at the front of this queue.
     *
     * <p>Pre-condition: The queue must not be empty.</p>
     * <p>Post-condition: The front element is removed and the queue size
     * decreases by one.</p>
     *
     * @return The first item in the queue.
     * @throws EmptyQueueException If the queue's length is zero.
     */
    @Override
    public E dequeue() throws EmptyQueueException
    {
        if ( isEmpty() )
            throw new EmptyQueueException( "Cannot dequeue from an empty queue." );
        return list.remove( 0 );
    }

    /**
     * Returns the element at the front of this queue without removing it.
     *
     * <p>Pre-condition: The queue must not be empty.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return The first item in the queue.
     * @throws EmptyQueueException If the queue's length is zero.
     */
    @Override
    public E peek() throws EmptyQueueException
    {
        if ( isEmpty() )
            throw new EmptyQueueException( "Cannot peek an empty queue." );
        return list.get( 0 );
    }

    /**
     * Removes all items in this queue.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue is empty and its size is zero.</p>
     */
    @Override
    public void dequeueAll()
    {
        list.clear();
    }

    /**
     * Returns {@code true} when this queue contains no items.
     *
     * @return {@code true} when the queue length is zero.
     */
    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    /**
     * Returns {@code true} if this queue contains the specified element.
     *
     * <p>Pre-condition: {@code toFind} must not be {@code null}.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @param toFind The element whose presence is to be tested.
     * @return {@code true} if the queue contains the element.
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
     * Returns the 1-based position from the front of this queue where the
     * specified element is located. The first item on the queue is at
     * distance 1. Returns {@code -1} if the object is not found.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @param toFind The desired object.
     * @return The 1-based position from the front of the queue, or
     *         {@code -1} if not found.
     */
    @Override
    public int search( E toFind )
    {
        if ( toFind == null )
            return -1;

        for ( int i = 0; i < list.size(); i++ )
        {
            if ( toFind.equals( list.get( i ) ) )
                return i + 1;
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in this queue, from front to
     * back, in proper sequence.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return An {@link Iterator} over the elements in this queue.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new QueueIterator();
    }

    /**
     * Compares the specified {@link QueueADT} with this queue for equality.
     * Two queues are equal if they have the same size and contain equal
     * items appearing in the same order.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: Both queues remain unchanged.</p>
     *
     * @param that The queue to compare with this queue.
     * @return {@code true} if the queues are equal.
     */
    @Override
    public boolean equals( QueueADT<E> that )
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
     * Returns an {@code Object} array containing all elements in this queue
     * in order from front to back.
     *
     * @return An array containing all elements of the queue, front to back.
     */
    @Override
    public Object[] toArray()
    {
        return list.toArray();
    }

    /**
     * Returns an array containing all elements in this queue in order from
     * front to back. If the given array is large enough it is used;
     * otherwise a new array of the same runtime type is allocated.
     *
     * @param toHold The array into which the elements of this queue are to
     *               be stored, if it is big enough.
     * @return An array containing all queue elements from front to back.
     * @throws NullPointerException If the specified array is {@code null}.
     */
    @Override
    public E[] toArray( E[] holder ) throws NullPointerException
    {
        if ( holder == null )
            throw new NullPointerException( "The provided array cannot be null." );
        return list.toArray( holder );
    }

    /**
     * Returns {@code true} if the queue is at capacity. Since this is a
     * dynamically sized queue with no fixed upper bound, this method always
     * returns {@code false}.
     *
     * @return {@code false} always, as this queue has no fixed capacity.
     */
    @Override
    public boolean isFull()
    {
        return false;
    }

    /**
     * Returns the length of the current queue as an integer value.
     *
     * @return The current size of the queue.
     */
    @Override
    public int size()
    {
        return list.size();
    }

    /**
     * Private inner class that implements the {@link Iterator} interface for
     * {@code MyQueue}. Iterates from the front of the queue to the back.
     */
    private class QueueIterator implements Iterator<E>
    {
        /** The underlying list iterator, which already walks front to back. */
        private final Iterator<E> listIterator;

        /**
         * Constructs a {@code QueueIterator} backed by the underlying list's
         * own iterator.
         */
        public QueueIterator()
        {
            listIterator = list.iterator();
        }

        /**
         * Returns {@code true} if there are more elements to iterate over.
         *
         * @return {@code true} if the iterator has more elements.
         */
        @Override
        public boolean hasNext()
        {
            return listIterator.hasNext();
        }

        /**
         * Returns the next element in the iteration, moving from front to
         * back.
         *
         * @return The next element.
         * @throws NoSuchElementException If the iteration has no more elements.
         */
        @Override
        public E next() throws NoSuchElementException
        {
            return listIterator.next();
        }
    }
}
