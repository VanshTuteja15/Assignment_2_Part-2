package implementations;

/**
 * {@code MyDLLNode} represents a single node used internally by
 * {@link MyDLL}. Each node stores an element of type {@code E} and holds
 * references to both the previous and next nodes in the list, allowing the
 * list to be traversed in either direction.
 *
 * @param <E> The type of element held by this node.
 * @author Vansh
 * @version 1.0
 */
public class MyDLLNode<E>
{
    /** The element stored in this node. */
    private E element;

    /** Reference to the previous node in the list, or {@code null} if this is the head. */
    private MyDLLNode<E> prev;

    /** Reference to the next node in the list, or {@code null} if this is the tail. */
    private MyDLLNode<E> next;

    /**
     * Constructs a new {@code MyDLLNode} holding the specified element, with
     * no links to any neighbouring nodes.
     *
     * @param element The element to be stored in this node.
     */
    public MyDLLNode( E element )
    {
        this.element = element;
        this.prev = null;
        this.next = null;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return The element stored in this node.
     */
    public E getElement()
    {
        return element;
    }

    /**
     * Replaces the element stored in this node.
     *
     * @param element The new element to store in this node.
     */
    public void setElement( E element )
    {
        this.element = element;
    }

    /**
     * Returns the node preceding this node in the list.
     *
     * @return The previous node, or {@code null} if this node is the head.
     */
    public MyDLLNode<E> getPrev()
    {
        return prev;
    }

    /**
     * Sets the node preceding this node in the list.
     *
     * @param prev The node to set as the previous node.
     */
    public void setPrev( MyDLLNode<E> prev )
    {
        this.prev = prev;
    }

    /**
     * Returns the node following this node in the list.
     *
     * @return The next node, or {@code null} if this node is the tail.
     */
    public MyDLLNode<E> getNext()
    {
        return next;
    }

    /**
     * Sets the node following this node in the list.
     *
     * @param next The node to set as the next node.
     */
    public void setNext( MyDLLNode<E> next )
    {
        this.next = next;
    }
}
