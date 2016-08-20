package game.model;

import java.util.Iterator;
import java.util.LinkedList;

public class MyLinkedList<E> implements Iterable<E> {
	private LinkedList<E> list = new LinkedList<E>();

	public void add(E element) {
		list.add(element);
	}

	public E get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}

	@Override
	public Iterator<E> iterator() {

		return new Iter();
	}

	class Iter implements Iterator<E> {

		private Iterator<E> iter;

		public Iter() {
			iter = list.iterator();
		}

		@Override
		public boolean hasNext() {

			return iter.hasNext();
		}

		@Override
		public E next() {

			return iter.next();
		}

	}

}
