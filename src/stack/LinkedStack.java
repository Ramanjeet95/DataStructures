package stack;

import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.Iterator;

public class LinkedStack<T> implements Iterable<T>
{
	private Node<T> top;
	private int len;
	private transient int modCount;
	
	public LinkedStack() {}
	
	private class Node<D>
	{
		private D data;
		private Node<D> prevNode;
		private Node<D> nextNode;
		
		public Node(D data, Node<D> prevNode, Node<D> nextNode) 
		{
			this.data = data;
			this.prevNode = prevNode;
			this.nextNode = nextNode;
		}

		@Override
		public String toString() 
		{
			return data != null ? data.toString() : null;
		}
	}
	
	public T pop()
	{
		if(isEmpty()) throw new EmptyStackException();
		T obj = top.data;
		top = top.nextNode;
		if(--len != 0)
			top.prevNode = null;
		modCount++;
		return obj;
	}
	
	public void push(T obj)
	{
		if(isEmpty())
			top = new Node<T>(obj, null, null);
		else
		{
			top = new Node<T>(obj, null, top);
			top.nextNode.prevNode = top;
		}
		len++;
		modCount++;
	}
	
	public T peek()
	{
		if(isEmpty()) throw new EmptyStackException();
		return top.data;
	}
	
	public int size()
	{
		return len;
	}
	
	public boolean isEmpty()
	{
		return len == 0;
	}
	
	public boolean contains(T obj)
	{
		return indexOf(obj) != -1;
	}
	
	public int indexOf(T obj)
	{
		Node<T> trav;
		int i;
		if(obj == null)
		{
			for(i = 0, trav = top; trav != null; trav = trav.nextNode, i++)
			{
				if(trav.data == null)
					return i;
			}
		}
		else
		{
			for(i = 0, trav = top; trav != null; trav = trav.nextNode, i++)
			{
				if(obj.equals(trav.data))
					return i;
			}
		}
		return -1;
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>() 
		{
			private int expectedModCount = modCount;
			private int cursor;
			private Node<T> trav;
			
			@Override
			public boolean hasNext() 
			{
				return trav != null;
			}

			@Override
			public T next() 
			{
				if(expectedModCount != modCount)
					throw new ConcurrentModificationException();
				T data = trav.data;
				trav = trav.nextNode;
				cursor++;
				return data;
			}
		};
	}
	
	public void clear()
	{
		top = null;
		len = 0;
		modCount++;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder str = new StringBuilder("[");
		Node<T> trav;
		if(top != null)
		{
			for(trav = top; trav != null; trav = trav.nextNode)
				str.append(trav + ", ");
		}
		return (str.length() > 1 ? str.substring(0, str.lastIndexOf(",")) : str) + ", top = "+ top + "]";
	}

	public static void main(String[] args)
	{
		LinkedStack<String> stack = new LinkedStack<String>(); 
		stack.push("Raman1");
		stack.push("Raman2");
		stack.toString();
		stack.push("Raman3");
		stack.push("Raman4");
		stack.push("Raman5");
		stack.push("Raman6");
		stack.push("Raman7");
		stack.pop();
		stack.pop();
		stack.pop();
	}
}
