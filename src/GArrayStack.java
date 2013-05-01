/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
 * Project 2
*/

// Creates a stack for a generic type using an array

import java.util.EmptyStackException;

public class GArrayStack<E> implements GStack<E>{
	private E[] stack; 
	private int count; //current number of objects in the stack
	
	/*
	 * Constructs an ArrayStack storing type E
	 */
	public GArrayStack(){
		stack = (E[])new Object[10]; 
		count = 0;
	}
	
	/*
	 * Checks to see if the ArrayStack is empty, returns true if the ArrayStack
	 * is empty otherwise returns false.
	 */
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		return false;
	}

	/*
	 * Takes element d of type E as a parameter and adds it to the ArrayStack 
	 */
	public void push(E d) {
		if(count == stack.length) {
			stack = doubleArray(stack); 
		}
		count++;
		stack[count-1] = d; 	
	}

	/*
	 * Returns the most recent element added to the ArrayStack and removes it.
	 * Element returned is of type E. Throws EmptyStackException if the 
	 * ArrayStack is empty.
	 */
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		E value = stack[count-1]; 
		stack[count-1] = null; 
		count--; 
		return value;
	}

	/*
	 * Returns the most recent element added to the ArrayStack. Returned
	 * element is of type E. Throws EmptyStackException if the 
	 * ArrayStack is empty. 
	 */
	public E peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return stack[count-1];
	}
	
	/*
	 * Doubles the length of the generic array passed and copies over all 
	 * existing elements into the new array, preserving the order. Returns
	 * the new generic array. 
	 */
	private E[] doubleArray(E[] original) {
		E[] newArray = (E[])new Object[original.length * 2];
		for(int i = 0; i < original.length; i++) {
			newArray[i] = original[i];
		}
		return newArray;
	}
}
