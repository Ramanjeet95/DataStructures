package queue;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class StaticArrayQueue<T> implements Iterable<T> 
{
	private T arr[];
	private int len;
	private int front;
	private int back;
	private transient int modCount;
	
	StaticArrayQueue(int capacity)
	{
		arr = (T[])new Object[capacity];
	}
	//Bad Implementation --> O(1) but poll is O(n)
//	public void offer(T obj)
//	{
//		if(len + 1 > arr.length)
//			throw new RuntimeException("Queue capacity exceeded");
//		arr[len++] = obj;
//		modCount++;
//	}
	
//	public void offer(T obj)
//	{
//		if(len + 1 > arr.length)
//			throw new RuntimeException("Queue capacity exceeded");	
//		if(back > arr.length-1)
//			back = 0;
//		arr[back++] = obj;
//		len++;
//		modCount++;	
//	}
	
	public void offer(T obj) 
	{
        if (len + 1 > arr.length) {
        	throw new RuntimeException("Queue capacity exceeded");
        }
        else 
        {
            back = (back + 1) % arr.length;
            arr[back] = obj;
            len++;
            
            if (front == -1) 
            {
				front = back;
			}
        }
    }
	
	
	//Bad Implementation --> O(n)
//	public T poll()
//	{
//		if(isEmpty())
//			throw new RuntimeException("Queue is empty");
//		T data = arr[0];
//		for(int i = 0; i < len -1; i++)
//			arr[i] = arr[i+1];
//		arr[--len] = null;	
//		modCount++;	
//		return data;
//	}
	
//	public T poll()
//	{
//		if(isEmpty())
//			throw new RuntimeException("Queue is empty");
//		T data = arr[front];
//		arr[front++] = null;
//		if(--len == 0)
//			front = back = 0;
//		modCount++;	
//		return data;
//	}
	
	public T poll(){
        T deQueuedElement;
        if (isEmpty()) 
        {
        	throw new RuntimeException("Queue is empty");        }
        else 
        {
            deQueuedElement = arr[front];
            arr[front] = null;
            front = (front + 1) % arr.length;
            len--;
        }
        return deQueuedElement;
    }
	
	public T peek()
	{
		if(isEmpty())
			throw new RuntimeException("Queue is empty");
		
		return arr[0];
	}
	
	public boolean isEmpty()
	{
		return len == 0;
	}
	
	public boolean contains(T obj)
	{
		return indexOf(obj) != -1;
	}
	
//	public int indexOf(T obj)
//	{
//		if(obj == null)
//		{
//			for(int i = 0; i < len; i++)
//			{
//				if(arr[i] == null)
//					return i;
//			}
//		}
//		else
//		{
//			for(int i = 0; i < len; i++)
//			{
//				if(obj.equals(arr[i]))
//					return i;
//			}
//		}
//		return -1;
//	}
	
	public int indexOf(T obj)
	{
		int index = 0;
		if(obj == null)
		{
			for(int i = 0; i < len; i++)
			{
				if(arr[i+front] == null)
					return index;
				if(i == len - 1)
					i = 0;
			}
		}
		else
		{
			for(int i = front; i < back; i++, index++)
			{
				if(obj.equals(arr[i]))
				{
					return index;
				}
				if(i == len - 1)
					i = 0;
			}
		}
		return -1;
	}
	
	public int size()
	{
		return len;
	}
	
	@Override
	public String toString() 
	{
		return "[" + Arrays.toString(arr) + "]";
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
				
				return arr[cursor++];
			}
		};
	}
	public static void main(String[] args) 
	{
		StaticArrayQueue<String> queue = new StaticArrayQueue<String>(10);
		queue.offer("one");
		queue.offer("two");
		queue.offer("three");
		queue.offer("four");
		queue.offer("five");
		queue.offer("six");
		queue.offer("seven");
		queue.offer("eight");
		queue.offer("nine");
		queue.offer("ten");
		
		queue.poll();
		queue.offer("ten");
		queue.poll();
		queue.poll();
		queue.poll();
		queue.offer("eleven");
		
		queue.indexOf("eleven");
		System.out.println(queue);
	}
}
