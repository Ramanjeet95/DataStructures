package linkedlist;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>
{
	private Node<T> head;
	private Node<T> tail;
	private int len;
	private transient int modCount;
	LinkedList(){}

	private class Node<D>
	{
		private D data;
		private Node<D> nextNode;
		
		Node(D data, Node<D> nextNode)
		{
			this.data = data;
			this.nextNode = nextNode;
		}

		@Override
		public String toString() 
		{
			return data == null ? null : data.toString();
		}
	}
	
	public void add(T obj)
	{
		Node<T> node = new Node<T>(obj, null);
		if(isEmpty())
			head = tail = node;
		else
		{
			tail.nextNode = node;
			tail = node;
		}
		modCount++;
		len++;
	}
	
	public void addFirst(T obj)
	{
		if(isEmpty())
			head = tail = new Node<T>(obj, null);
		else
			head = new Node<T>(obj, head);
		modCount++;
		len++;
	}
	
	public T removeFirst() 
	{
		if(isEmpty()) {throw new RuntimeException("List is empty");} 
		T data = head.data;
		head = head.nextNode;
		if(--len == 0)
			tail = null;
		modCount++;
		return data;
	}
	
	public T removeLast()
	{
		if(isEmpty()) {throw new RuntimeException("List is empty");} 
		return remove(len - 1);
	}
	
//	public T remove(int index)
//	{
//		if(isEmpty()) throw new RuntimeException("List is empty");
//		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
//		Node<T> prevNode;
//		Node<T> nodeToBeRemoved;
//		int i;
//		for(i = 0, prevNode = null, nodeToBeRemoved = head; index != i; i++)
//		{
//			prevNode = nodeToBeRemoved;
//			nodeToBeRemoved = nodeToBeRemoved.nextNode;
//		}
//		return removeNextNode(prevNode, nodeToBeRemoved);
//	}
	
	public T remove(int index)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		if(index == 0)
			return removeFirst();
		Node<T> trav;
		int i;
		for(i = 0, trav = head; index - 1 != i; i++)
			trav = trav.nextNode;
		return removeNextNode(trav);
	}
	
	public boolean remove(T obj)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		Node<T> prevNode = null;
		Node<T> trav;
		
		if(obj == null)
		{
			for(trav = head; trav != null; prevNode = trav, trav = trav.nextNode)
			{
				if(trav.data == null)
				{
					removeNextNode(prevNode);
					return true;
				}
			}
		}
		else
		{
			for(trav = head; trav != null; prevNode = trav, trav = trav.nextNode)
			{
				if(obj.equals(trav.data))
				{
					removeNextNode(prevNode);
					return true;
				}
			}
		}
		return false;
	}
	
	public int indexOf(T obj)
	{
		Node<T> trav;
		int i;
		if(obj == null)
		{
			for(i = 0, trav = head; trav != null; trav = trav.nextNode, i++)
			{
				if(trav.data == null)
					return i;
			}
		}
		else
		{
			for(i = 0, trav = head; trav != null; trav = trav.nextNode, i++)
			{
				if(obj.equals(trav.data))
					return i;
			}
		}
		return -1;
	}
	
//	private T remove(Node<T> prevNode, Node<T> nodeToBeRemoved)
//	{
//		if(head == nodeToBeRemoved)
//			return removeFirst();
//		if(tail == nodeToBeRemoved)
//			tail = prevNode;
//		prevNode.nextNode = nodeToBeRemoved.nextNode;
//		len--;
//		modCount++;
//		return nodeToBeRemoved.data;
//	}
	
	private T removeNextNode(Node<T> prevNode)
	{
		if(prevNode == null)
			return removeFirst();
		if(tail == prevNode.nextNode)
			tail = prevNode;
		T data = prevNode.nextNode.data;
		prevNode.nextNode = prevNode.nextNode.nextNode;
		len--;
		modCount++;
		return data;
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
			
			for(i = 0, trav = head; i != index - 1; i++)
				trav = trav.nextNode;
			trav.nextNode = new Node<T>(obj, trav.nextNode);
			len++;
			modCount++;
		}
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
	
	public boolean isEmpty()
	{
		return len == 0;
	}
	
	public int size()
	{
		return len;
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
	public String toString() 
	{
		StringBuilder obj = new StringBuilder("[");
		Node<T> trav; 
		if(head != null)
		{
			for(trav = head; trav != null; trav = trav.nextNode)
				obj.append(trav.data + ", ");
		}
		return (obj.length() > 1 ? obj.substring(0, obj.lastIndexOf(",")) : obj.toString()) + "]";
	}

	@Override
	public Iterator<T> iterator() 
	{
		return new Iterator<T>()
		{
			private Node<T> trav;
			private int expectedModCount;

			@Override
			public boolean hasNext() 
			{
				return trav != null;
			}

			@Override
			public T next() 
			{
				if(modCount != expectedModCount)
					throw new ConcurrentModificationException();
				T data = trav.data;
				trav = trav.nextNode;
				return data;
			}
		};
	}
	
	public static void main(String[] args) 
	{
		long startTime = System.currentTimeMillis();
		LinkedList<String> list = new LinkedList<String>();
//		ArrayList<String> list = new ArrayList<String>();
		list.add("Raman1");
		//list.remove("Raman1");
		list.add("Suresh");
		list.removeFirst();
		list.add("Raman2");
		list.add("haha1");
		list.add("haha2");
		list.toString();
		list.add("haha3");
		//list.clear();
		list.add(0, null);
		list.add(4, "middle");
		list.set(5, "NANANA");
		list.remove(0);
		list.remove("middle");
		list.removeLast();
		list.get(5);
		list.set(3, "SCWIFTHY!!");
		//list.add(7, "secondSecondLast");
		list.add(null);
		list.remove(null);
		for(String s : list)
		{
			System.out.println(s);
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}
}
