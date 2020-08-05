package arrays;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T>
{
	private T[] arr;
	private int len;
	private int capacity;
	private transient int modCount;
	
	DynamicArray()
	{
		this(5);
	}
	
	DynamicArray(int capacity)
	{
		arr = (T[])new Object[capacity];
		this.capacity = capacity;
	}
	
	public T set(int index, T obj)
	{
		if(index < 0 || index >= len)
			throw new ArrayIndexOutOfBoundsException();
		T previousValue = get(index);
		arr[index] = obj;
		return previousValue;
	}
	
	public T get(int index)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len)
			throw new ArrayIndexOutOfBoundsException();
		return arr[index];
	}
	
	public void add(T obj)
	{
		if((len + 1) >= capacity)
		{
			if(capacity == 0) 
				capacity = 1;
			else
			{
				capacity *= 2;
				arr = Arrays.copyOf(arr, capacity);
			}
		}
		arr[len++] = obj;
		modCount++;
	}
	
	public void add(int index, T obj)
	{
		if(index < 0 || index >= len) throw new ArrayIndexOutOfBoundsException();
		if((len + 1) >= capacity)
		{
			if(capacity == 0) 
				capacity = 1;
			else
				capacity *= 2;
			
			arr = Arrays.copyOf(arr, capacity);
		}
		for(int i = 0, j = 0 ; i < len ; i++ , j++)
		{
			if(i == index) i++;
			arr[i] = arr[j];
		}
		arr[index] = obj;
		len++;
		modCount++;
	}
	
	public int indexOf(T obj)
	{
		for(int i = 0; i < len; i++)
		{
			if(arr[i].equals(obj))
				return i;
		}
		return -1;
	}
	
	public boolean contains(T obj)
	{
		return indexOf(obj) != -1;
	}
	
	public void remove(T obj)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		int index = this.indexOf(obj);
		if(index == -1)
			return;
		else
		{
			for(int i = index; i < len-1; i++)
			{
				arr[i] = arr[i+1];
			}
			arr[--len] = null;
			modCount++;
		}
	}
	
	public void remove(int index)
	{
		remove(get(index));
	}
	
	public int size()
	{
		return len;
	}
	
	public boolean isEmpty()
	{
		return len == 0;
	}
	
	public void clear()
	{
		for(int i = 0; i < len; i++)
			arr[i] = null;
		modCount++;
	}
	
	@Override
	public String toString() 
	{
		return Arrays.toString(Arrays.copyOf(arr, len));
	}

	@Override
	public Iterator<T> iterator() 
	{
		return new ArrayIterator<T>(this);
	}
	
	private class ArrayIterator<T> implements Iterator<T>
	{
		private DynamicArray<T> array;
		private int cursor = 0;
		private int expectedModCount;
		
		public ArrayIterator(DynamicArray<T> array) 
		{
			this.array = array;
			expectedModCount = array.modCount;
		}

		@Override
		public boolean hasNext() 
		{
			return cursor < array.size();
		}

		@Override
		public T next() 
		{
			if(expectedModCount != array.modCount)
				throw new ConcurrentModificationException();
			return array.get(cursor++);
		}
		
	}
	public static void main(String[] args) 
	{
		long startTime = System.currentTimeMillis();
		DynamicArray<String> array = new DynamicArray<>();
		array.add("Raman1");
		array.add("Raman2");
		array.add("Raman3");
		array.add("Raman4");
		array.add("hahaha");
		array.add("apple");
		System.out.println(array.capacity +" "+ array.len);
		array.remove("Raman3");
			System.out.println(array);
			System.out.println(array.capacity +" "+ array.len);
		System.out.println(System.currentTimeMillis() - startTime);


	}
}
