package PomocneTridy;

import Exception.HashMapException;

public class UniqueHashMap<K,V> extends java.util.HashMap<K,V>{
		
	public UniqueHashMap() {
		super();
	}
	/** Method put insert value into map.
	 * Method manage synchronizet put a value into a map. 
	 * If map has already contained value for insert key, this value will be kept.*/
	@Override
	public synchronized V put(K key,V value) {
		V navrat =super.put(key, value);
		if(navrat!=null) {
			super.put(key, navrat);
		}
		return navrat;
	}
	
	/**Method manage  synchronized nserting value into map
	 * @throws HashMapException If map contains value for insert key, HashMapException will be thrown */
	public synchronized V UniquePut(K key, V value) throws HashMapException {
		V navrat =super.put(key, value);
		if(navrat!=null) {
			super.put(key, navrat);
			throw new HashMapException();
		}
		return navrat;
	}
}
	