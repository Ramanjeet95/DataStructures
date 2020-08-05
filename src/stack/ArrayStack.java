package stack;

import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.Iterator;

public class ArrayStack<T> implements Iterable<T>
{
	private T top;
	private T arr[];
	private int capacity;
	private int len;
	private transient int modCount;
	
	ArrayStack()
	{
		this(10);
	}
	
	ArrayStack(int capacity)
	{
		arr = (T[])new Object[capacity];
		this.capacity = capacity;
	}
	
	ArrayStack(T obj)
	{
		this();
		push(obj);
	}
	
	public T pop()
	{
		if(isEmpty()) throw new EmptyStackException();
		T obj = top; 
		arr[--len] = null;
		if(len > 0)
			top = arr[len-1];
		else
			top = null;
		modCount++;
		return obj;
	}
	
	public void push(T obj)
	{
		if(len + 1 > capacity)
		{
			capacity = capacity * 2;
			T[] tempArray = (T[])new Object[capacity];
			for(int i = 0; i < len; i++)
				tempArray[i] = arr[i];
			arr = tempArray;
		}
		arr[len++] = obj;
		top = obj;
		modCount++;
	}
	
	public T peek()
	{
		if(isEmpty()) throw new EmptyStackException();
		return top;
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
		if(obj == null)
		{
			for(int i = 0; i < len; i++)
			{
				if(arr[i] == null)
					return len - i - 1;
			}
		}
		else
		{
			for(int i = 0; i < len; i++)
			{
				if(obj.equals(arr[i]))
					return len - i - 1;
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
				return arr[len - ++cursor];
			}
		};
	}
	
	@Override
	public String toString() 
	{
		StringBuilder str = new StringBuilder("[");
		for(int i = len - 1; i >= 0; i--)
			str.append(arr[i]/* != null ? arr[i].toString() : null */ + ", ");
		return (str.length() > 1 ? str.substring(0, str.lastIndexOf(",")) : str) + ", top = "+ top + "]";
	}

	public static void main(String[] args)
	{
		ArrayStack<String> stack = new ArrayStack<String>(); 
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
