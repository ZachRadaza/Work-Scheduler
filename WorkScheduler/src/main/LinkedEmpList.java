package main;

public class LinkedEmpList{
	private int day;
	private Node head;
	private int size;
	
	private class Node{
		Employee data;
		Node next;

	}
	
	public LinkedEmpList(int day){
		this.day = day;
		this.size = 0;
	}
	
	//adds it in order of availability in a day, greater hours bias
	public void add(Employee employee){
		Node newNode = new Node();
		newNode.data = employee;
		if(head == null){
			head = newNode;
		//other add case, more than head
		} else {
			Node current = head;
			addHelper(current, newNode);
		}
		size++;
	}
	
	private void addHelper(Node current, Node newNode){
		Node temp;
		//if no more is next
		if(current.next == null){
			current.next = newNode;
		//if both equal in opening
		} else if(newNode.data.getAvailabilityHours(day, 0) == current.next.data.getAvailabilityHours(day, 0)){
			if(newNode.data.getAvailabilityHours(day, 1) >= head.data.getAvailabilityHours(day, 1)){
				temp = current.next;
				current.next = newNode;
				if(current.next.next == null)
					current.next.next = new Node();
				current.next.next = temp;
			//if new node is greater than
			} else {
				addHelper(current.next, newNode);
			}
			
		} else if(newNode.data.getAvailabilityHours(day, 0) < current.next.data.getAvailabilityHours(day, 0)){
			temp = current.next;
			current.next = newNode;
			current.next.next = temp;
		} else {
			addHelper(current.next, newNode);
		}
	}
	
	public Employee remove(int index){
		Node temp;
		if(index >= size || head == null){
			return null;
		} else if (index == 0 && head.next == null){
			temp = head;
			head = null;
			size--;
			return temp.data;
		} else if(index == 0 && head.next != null){
			temp = head;
			head = head.next;
			return temp.data;
		} else if(index == size - 1){
			Node current = head;
			Node prev = head;
			while(current.next != null){
				prev = current;
				current = current.next;
			}
			temp = prev.next;
			prev.next = null;
			return temp.data;
		} else {
			Node current = head;
			Node prev = head;
			int i = 0;
			while(i < index){
				prev = current;
				current = current.next;
				i++;
			}
			temp = current;
			prev.next = current.next;
			size--;
			return temp.data;
		}
	}
	
	public Employee get(int index){
		if(index >= size || head == null){
			return null;
			
		} else if (index == 0){
			return head.data;
			
		} else {
			Node current = head;
			int i = 0;
			while(i < index){
				current = current.next;
				i++;
			}
			return current.data;
		}
	}
	
	public boolean isEmpty(){
		if(head == null){
			return true;
		} else {
			return false;
		}
	}
	
	public int size(){
		return this.size;
	}
	
	@Override
	public String toString(){
		String ret = "";
		return ret;
	}
}