package utilities;

import exceptions.EmptyQueueException;

/**
 * The {@code QueueADT} interface defines the abstract data type for a
 * queue data structure to be used in CPRG304 at SAIT Polytechnic.
 *
 * <p>A queue follows the First-In-First-Out (FIFO) principle, meaning the
 * first element enqueued is the first one to be dequeued. This interface
 * provides all standard queue operations as well as several helper methods
 * for additional flexibility.</p>
 *
 * <p>Implementations of this interface must not use any classes from
 * {@code java.util.*} collection framework as the underlying data
 * structure.</p>
 *
 * @param <E> The type of elements held in this queue.
 * @author Parneet Kaur, Vansh Tuteja, Heer and Sehaj
 * @version 1.0
 */
public interface QueueADT<E>
{
    /**
     * Adds the specified element to the end of this queue.
     *
     * <p>Pre-condition: The element to be enqueued must not be {@code null}.</p>
     * <p>Post-condition: The element is added to the end of the queue and
     * the size increases by one.</p>
     *
     * @param toAdd The element to be added to the end of the queue.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    public void enqueue( E toAdd ) throws NullPointerException;

    /**
     * Removes and returns the element at the front of this queue.
     *
     * <p>Pre-condition: The queue must not be empty.</p>
     * <p>Post-condition: The first element is removed from the queue and
     * the size decreases by one.</p>
     *
     * @return The element removed from the front of the queue.
     * @throws EmptyQueueException If the queue contains no elements.
     */
    public E dequeue() throws EmptyQueueException;

    /**
     * Returns the element at the front of this queue without removing it.
     *
     * <p>Pre-condition: The queue must not be empty.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return The element at the front of the queue.
     * @throws EmptyQueueException If the queue contains no elements.
     */
    public E peek() throws EmptyQueueException;

    /**
     * Removes all elements from this queue.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue is empty and its size is zero.</p>
     */
    public void dequeueAll();

    /**
     * Returns {@code true} if this queue contains no elements.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return {@code true} if the queue is empty; {@code false} otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns {@code true} if this queue contains the specified element.
     * More formally, returns {@code true} if and only if this queue contains
     * at least one element {@code e} such that {@code toFind.equals(e)}.
     *
     * <p>Pre-condition: {@code toFind} must not be {@code null}.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @param toFind The element whose presence in this queue is to be tested.
     * @return {@code true} if this queue contains the specified element;
     *         {@code false} otherwise.
     * @throws NullPointerException If the specified element is {@code null}.
     */
    public boolean contains( E toFind ) throws NullPointerException;

    /**
     * Returns the 1-based position from the front of the queue where the
     * specified element is located. The front element of the queue is
     * considered to be at distance 1. The {@code equals} method is used
     * to compare {@code toFind} to the elements in this queue.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @param toFind The element to search for in this queue.
     * @return The 1-based position from the front of the queue where the
     *         element is located, or {@code -1} if the element is not
     *         in the queue.
     */
    public int search( E toFind );

    /**
     * Returns an iterator over the elements in this queue in proper sequence,
     * from front to back.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return An {@link Iterator} over the elements in this queue from
     *         front to back.
     */
    public Iterator<E> iterator();

    /**
     * Compares the specified {@link QueueADT} with this queue for equality.
     * Two queues are considered equal if they contain equal elements
     * appearing in the same order from front to back.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: Both queues remain unchanged.</p>
     *
     * @param that The {@code QueueADT} to be compared to this queue.
     * @return {@code true} if the two queues are equal; {@code false} otherwise.
     */
    public boolean equals( QueueADT<E> that );

    /**
     * Returns an {@code Object} array containing all elements in this queue
     * in proper sequence from front to back.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return An array containing all elements of this queue in proper sequence.
     */
    public Object[] toArray();

    /**
     * Returns an array containing all elements in this queue in proper
     * sequence from front to back. The runtime type of the returned array
     * is that of the specified array. If the queue fits in the specified
     * array, it is returned therein; otherwise, a new array of the same
     * runtime type is allocated.
     *
     * <p>Pre-condition: The specified array must not be {@code null}.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @param toHold The array into which the elements of this queue are to
     *               be stored, if it is big enough; otherwise, a new array
     *               of the same runtime type is allocated for this purpose.
     * @return An array containing the elements of this queue.
     * @throws NullPointerException If the specified array is {@code null}.
     */
    public E[] toArray( E[] toHold ) throws NullPointerException;

    /**
     * Returns {@code true} if the number of elements in the queue equals
     * its fixed maximum capacity. This method is only meaningful when a
     * fixed-size queue is required; dynamically sized implementations
     * should always return {@code false}.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return {@code true} if the queue is at capacity; {@code false} otherwise.
     */
    public boolean isFull();

    /**
     * Returns the number of elements currently in this queue.
     *
     * <p>Pre-condition: None.</p>
     * <p>Post-condition: The queue remains unchanged.</p>
     *
     * @return The current number of elements in the queue as an integer.
     */
    public int size();
}