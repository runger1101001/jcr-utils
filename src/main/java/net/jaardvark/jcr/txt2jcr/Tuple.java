package net.jaardvark.jcr.txt2jcr;

public class Tuple<T> {

	public T[] contents;

	public Tuple(@SuppressWarnings("unchecked") T...contents){
		this.contents = contents;
	}
	
	public T[] getContents(){
		return contents;
	}
	
	public int size(){
		return contents.length;
	}
	
	public T get(int index){
		return contents[index];
	}
	
}
