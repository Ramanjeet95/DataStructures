package linkedlist;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;

public class DoublyLinkedList<T> implements Iterable<T>
{
	private Node<T> head;
	private Node<T> tail;
	private int len;
	private transient int modCount;
	
	public DoublyLinkedList(){}

	private class Node<D>
	{
		private D data;
		private Node<D> prevNode;
		private Node<D> nextNode;
		
		Node(D data, Node<D> prevNode, Node<D> nextNode)
		{
			this.data = data;
			this.prevNode = prevNode;
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
		Node<T> node = new Node<T>(obj, null, null);
		if(isEmpty())
			head = tail = node;
		else
		{
			tail.nextNode = node;
			node.prevNode = tail;
			tail = node;
		}
		len++;
		modCount++;
	}
	
	public void addFirst(T obj)
	{
		if(isEmpty())
			tail = head = new Node<T>(obj, null, head);
		else
		{
			head = new Node<T>(obj, null, head);
			head.nextNode.prevNode = head;
		}
		len++;
		modCount++;
	}
	
	public T removeFirst() 
	{
		if(isEmpty()) {throw new RuntimeException("List is empty");} 
		T data = head.data;
		if(--len == 0)
			head = tail = null;
		else
		{
			head = head.nextNode;
			head.prevNode = null;
		}
		modCount++;
		return data;
	}
	
	public T removeLast()
	{
		if(isEmpty()) {throw new RuntimeException("List is empty");} 
		T data = tail.data;
		if(--len == 0)
			head = tail = null;
		else
		{
			tail = tail.prevNode;
			tail.nextNode = null;
		}
		modCount++;
		return data;
	}
	
	public T remove(int index)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
	    return remove(getNode(index));
	}
	
	public boolean remove(T obj)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		Node<T> trav;
		
		if(obj == null)
		{
			for(trav = head; trav != null; trav = trav.nextNode)
			{
				if(trav.data == null)
				{
					remove(trav);
					return true;
				}
			}
		}
		else
		{
			for(trav = head; trav != null; trav = trav.nextNode)
			{
				if(obj.equals(trav.data))
				{
					remove(trav);
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
	
	private T remove(Node<T> node)
	{
		if(node == head)
			return removeFirst();
		else if(node == tail)
			return removeLast();
		
		T data = node.data;
		node.prevNode.nextNode = node.nextNode;
		node.nextNode.prevNode = node.prevNode;
		len--;
		modCount++;
		return data;
	}
	
	public T get(int index)
	{
		if(isEmpty()) throw new RuntimeException("List is empty");
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		return getNode(index).data;
	}
	
	public void set(int index, T obj)
	{
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		getNode(index).data = obj;
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
			Node<T> tempNode = getNode(index);
			tempNode.prevNode.nextNode = new Node<T>(obj, tempNode.prevNode, tempNode);
			tempNode.prevNode = tempNode.prevNode.nextNode;
			len++;
			modCount++;
		}
	}
	
	private Node<T> getNode(int index)
	{
		if(index < 0 || index >= len) throw new IndexOutOfBoundsException();
		Node<T> tempNode;
		int i;
		if(index < len/2)
		{
			//Search from the front of the list
			for(i = 0, tempNode = head; i != index; i++)
				tempNode = tempNode.nextNode;
		}
		else
		{
			//Search from the end of the list
			for(i = len - 1, tempNode = tail; i != index; i--)
				tempNode = tempNode.prevNode;
		}
		return tempNode;
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
	
	public void clear()
	{
		head = tail = null;
		len = 0;
		modCount++;
	}
	
	@Override
	public ListIterator<T> iterator() 
	{
		return new ListIteratorImpl<T>(this);
	}
	
	private class ListIteratorImpl<E> implements ListIterator<E>
	{
		private DoublyLinkedList<E>.Node<E> currentNode;
		private DoublyLinkedList<E> list;
		private int cursor;
		private int expectedModCount;
		private boolean isModifiedAfterNextOrPrev;
		private int lastRefIndex;
		
		public ListIteratorImpl(DoublyLinkedList<E> list) 
		{
			this.list = list;
			this.currentNode = list.head;
			expectedModCount = list.modCount;
		}

		@Override
		public boolean hasNext() 
		{
			return cursor < len;
		}

		@Override
		public E next() 
		{
			if(list.modCount != expectedModCount)
				throw new ConcurrentModificationException();
			E data = currentNode.data;
			currentNode = currentNode.nextNode;
			lastRefIndex = cursor++;
			isModifiedAfterNextOrPrev = false;
			return data;
		}

		@Override
		public void add(E e) 
		{
			if(list.modCount != expectedModCount)
				throw new ConcurrentModificationException();
			list.add(cursor, e);
			cursor++;
			expectedModCount++;
			isModifiedAfterNextOrPrev = true;
		}

		@Override
		public boolean hasPrevious() 
		{
			return cursor - 1 > -1;
		}

		@Override
		public int nextIndex() 
		{
			return cursor;
		}

		@Override
		public E previous() 
		{
			if(list.modCount != expectedModCount) throw new ConcurrentModificationException();
			currentNode = currentNode.prevNode;
			lastRefIndex = --cursor;
			isModifiedAfterNextOrPrev = false;
			return currentNode.data;
		}

		@Override
		public int previousIndex() 
		{
			return cursor - 1;
		}

		@Override
		public void remove()
		{
			if(list.modCount != expectedModCount) throw new ConcurrentModificationException();
			if(isModifiedAfterNextOrPrev) throw new IllegalStateException();
			list.remove(lastRefIndex);
			expectedModCount++;
			isModifiedAfterNextOrPrev = true;
		}

		@Override
		public void set(E e) 
		{
			if(list.modCount != expectedModCount) throw new ConcurrentModificationException();
			if(isModifiedAfterNextOrPrev) throw new IllegalStateException();
			list.getNode(lastRefIndex).data = e;
		}
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
	
	public static void main(String[] args) 
	{
		long startTime = System.currentTimeMillis();
		DoublyLinkedList<String> list = new DoublyLinkedList<String>();
//		List<String> list = new ArrayList<String>();
		list.add("Raman");
		list.remove("Raman");
		list.add("Suresh");
		list.add("haha");
		list.remove("Suresh");
		list.add("last");
		list.add("last2");
		list.add("last3");
		list.add("last4");
		list.add("last5");
		list.add(9, "PICKLERICKKKKKK");
		ListIterator<String> itr = list.iterator(); // head = haha, tail = last5, len = 7, modCount = 11
		itr.add("aha");// cursor = 1, expectedModCount = 12, head = aha, len = 8 
		int i = 0;
		itr.next();
		while (itr.hasPrevious())
		{
			String str = itr.previous(); // 
			//String str2 = itr.previous(); 
			itr.set("Surprise");
			if(i == 3)
				itr.remove();
				//itr.add("GET SCWIFTHY!");
			itr.set("WILDcard");
			i++;
		}
//		System.out.println(list);
//		list.remove(3);
//		list.add(4, "Fourth");
//		list.addFirst("First");
//		list.add("LastLast");
//		list.removeLast();
//		list.removeFirst();
//		System.out.println(list);
//		System.out.println(System.currentTimeMillis() - startTime);
	}
}

