package linkedlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class CircularLinkedList<T> implements Iterable<T>
{
	private Node<T> head;
	private Node<T> tail;
	private int len;
	private transient int modCount;
	
	public CircularLinkedList(){}
	
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
	
	private void addFirstElement(T obj)
	{
		tail = new Node<>(obj, null);
		tail.nextNode = tail;
		head = tail;
	}
	
	public void add(T obj)
	{
		if(isEmpty())
			addFirstElement(obj);
		else
		{
			tail.nextNode = new Node<>(obj, head);
			tail = tail.nextNode;
		}
		len++;
		modCount++;
	}
	
	public void addFirst(T obj)
	{
		if(isEmpty())
			addFirstElement(obj);
		else
		{
			head = new Node<T>(obj, head);
			tail.nextNode = head;
		}
		len++;
		modCount++;
	}
	
	public void add(int index, T obj)
	{
		if(index < 0 || index > len) throw new IndexOutOfBoundsException();
		if(index == 0)
			addFirst(obj);
		else if(index == len)
			add(obj);
		else
		{
			Node<T> trav;
			int i;
			for(i = 1, trav = head.nextNode; i != index - 1; i++)
				trav = trav.nextNode;
			trav.nextNode = new Node<T>(obj, trav.nextNode);
			len++;
			modCount++;
		}
	}
	
	
	public T remove()
	{
		return remove(len-1);
//		T data;
//		if(--len == 0)
//		{
//			data = tail.data;
//			head = tail = null;
//		}
//		else
//		{
//			Node<T> trav;
//			for(trav = head; trav.nextNode != tail; trav = trav.nextNode);
//			tail = trav;
//			tail.nextNode = head;
//			data = trav.data;
//		}
//		modCount++;
//		return data;
	}
	
	public T removeFirst()
	{
		if(isEmpty())
			throw new RuntimeException("List is empty");
		T data;
		if(--len == 0)
		{
			data = tail.data;
			head = tail = null;
		}
		else
		{
			data = head.data;
			head = head.nextNode;
			tail.nextNode = head;
		}
		modCount++;
		return data;	
	}
	
	public T remove(int index)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		Node<T> trav;
		int i;
		for(i = -1, trav = tail; i != index - 1; i++)
			trav = trav.nextNode;
		return removeNextNode(trav);
	}
	
	public boolean remove(T obj)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		Node<T> trav = tail;
		if(obj == null)
		{
			for(int i = 0; i < len; trav = trav.nextNode, i++)
			{
				if(trav.nextNode.data == null)
				{
					removeNextNode(trav);
					return true;
				}
			}
		}
		else
		{
			for(int i = 0; i < len; trav = trav.nextNode, i++)
			{
				if(obj.equals(trav.nextNode.data))
				{
					removeNextNode(trav);
					return true;
				}
			}
		}
		return false;
	}
	
	private T removeNextNode(Node<T> node)
	{
		if(node.nextNode == head)
			return removeFirst();
		
		T data = node.nextNode.data;
		if(node.nextNode == tail)
			tail = node;
		node.nextNode = node.nextNode.nextNode;
		len--;
		modCount++;
		return data;
	}
	
	public int indexOf(T obj)
	{
		Node<T> trav = head;
		if(obj == null)
		{
			for(int i = 0; i < len; trav = trav.nextNode, i++)
			{
				if(trav.data == null)
					return i;
			}
		}
		else
		{
			for(int i = 0; i < len; trav = trav.nextNode, i++)
			{
				if(obj.equals(trav.data))
					return i;
			}
		}
		return -1;
	}
	
	public T get(int index)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		Node<T> trav;
		int i;
		for(i = 0, trav = head; i != index; trav = trav.nextNode, i++);
		return trav.data;	
	}
	
	public void set(int index, T obj)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		Node<T> trav;
		int i;
		for(i = 0, trav = head; i != index; trav = trav.nextNode, i++);
		trav.data = obj;	
	}
	
	public boolean isEmpty()
	{
		return len == 0;
	}
	
	public void clear()
	{
		head = tail = null;
		len = 0;
		modCount++;
	}
	
	public boolean contains(T obj)
	{
		return indexOf(obj) != -1;
	}
	
	public T peekFirst()
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		return head.data;
	}
	
	public T peekLast()
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		return tail.data;
	}
	
	@Override
	public Iterator<T> iterator() 
	{
		return new Iterator<T>() 
		{
			private int expectedModCount = modCount;
			private int cursor;
			private Node<T> trav = head;
			
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
				if(trav == head)
					cursor = 0;
				cursor++;
				return data;
			}
		};
	}

	@Override
	public String toString() 
	{
		StringBuilder str = new StringBuilder("[");
		Node<T> trav;
		int i;
		for(i = 0, trav = head; i < len; trav = trav.nextNode, i++)
			str.append(trav.data + ", ");
		return (str.length() > 1 ? str.substring(0, str.lastIndexOf(",")) : str.toString()) + "]";
	}
	
	public static void main(String[] args) 
	{
		CircularLinkedList<String> list = new CircularLinkedList<String>();
		list.addFirst("third");
		list.add("fourth");
		list.add("fifth");
		list.add("sixth");
		list.add("eighth");
		list.addFirst("second");
		list.add(0, "first");
		list.add(6, "seventh");
		list.add(8, "Ninth");
		list.addFirst(null);
		
//		list.removeFirst();
		list.remove(0);
		list.remove(8);
//		list.remove(null);
//		list.remove(3);
//		list.remove(5);
		Iterator<String> itr = list.iterator();
		while(itr.hasNext())
		{
			String s = itr.next();
			System.out.println(s);
		}
		
	} 
	
}
