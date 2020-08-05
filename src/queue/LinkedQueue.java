package queue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinkedQueue<T> implements Iterable<T> 
{
	private Node<T> frontNode;
	private Node<T> backNode;
	private int len;
	private transient int modCount;
	
	public LinkedQueue(){}
	
	private class Node<D>
	{
		private D data;
		private Node<D> nextNode;
		
		public Node(D data, Node<D> nextNode) 
		{
			this.data = data;
			this.nextNode = nextNode;
		}

		@Override
		public String toString() 
		{
			return data != null ? data.toString() : null;
		}
	}
	
	public void offer(T obj)
	{
		if(isEmpty())
			frontNode = backNode = new Node<T>(obj, null);	
		else
			backNode = backNode.nextNode = new Node<T>(obj, null);
		len++;
		modCount++;
	}
	
	
	public T poll()
	{
		if(isEmpty())
			throw new RuntimeException("Queue is empty");
		
		T data = frontNode.data;
		if(--len == 0)
			frontNode = backNode = null;
		else
			frontNode = frontNode.nextNode;
		
		modCount++;
		return data;
	}
	
	public T peek()
	{
		if(isEmpty())
			throw new RuntimeException("Queue is empty");
		
		return frontNode.data;
	}
	
	public boolean isEmpty()
	{
		return len == 0;
	}
	
	public int indexOf(T obj)
	{
		Node<T> trav;
		int i;
		if(obj == null)
		{
			for(i = 0, trav = frontNode; trav != null; trav = trav.nextNode)
			{
				if(trav.data == null)
					return i;
			}
		}
		else
		{
			for(i = 0, trav = frontNode; trav != null; trav = trav.nextNode)
			{
				if(obj.equals(trav.data))
					return i;
			}	
		}
		return -1;
	}
	
	public boolean contains(T obj)
	{
		return indexOf(obj) != -1;
	}
	
	public int size()
	{
		return len;
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>() 
		{
			private int expectedModCount = modCount;
			private Node<T> trav = frontNode;
			private int cursor;
			
			@Override
			public boolean hasNext()
			{
				return cursor < len;
			}

			@Override
			public T next() 
			{
				if(expectedModCount != modCount)
					throw new ConcurrentModificationException();
				T data = frontNode.data;
				trav = trav.nextNode;
				cursor++;
				return data;
			}
		};
	}
	
}
