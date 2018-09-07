/*
 * Lab 5 oleh Bintang Ilham Syahputra, SI 2016
 * 1/11/2017
 */
import java.util.*;
import java.io.*;

public class SDA1718L5A_Selasa {

	public static void main(String[] args) throws IOException{
		
		// variabel input
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		
		String[] command;
		
		int jumlahAngka  =Integer.parseInt(input.readLine());
		String[] angka  = input.readLine().split(" ");
		int jumlahPerintah = Integer.parseInt(input.readLine());
		
		// membuat binarySearch tree
		BinarySearchTree<Integer> BST = new BinarySearchTree<>();
		
		for(int x = 0;x<jumlahAngka;x++){
			BST.makeBST(Integer.parseInt(angka[x]));
		}

		for(int x =0;x<jumlahPerintah;x++){
			command = input.readLine().split(" ");
			
			switch(command[0]){
			
			case("ADD"):
				BST.add(Integer.parseInt(command[1]));
				break;
			case("FIND"):
				BST.find(Integer.parseInt(command[1]));
				break;
			case("DELETE"):
				BST.remove(Integer.parseInt(command[1]));
				break;
			case("PREORDER"):
				BST.preOrder();
				break;
			case("INORDER"):
				BST.inOrder();
				break;
			case("POSTORDER"):
				BST.postOrder();
				break;
			}
		}
	}
}

// membuat class Binary Search tree


class BinarySearchTree<E extends Comparable<E>>{
	
	// private class node
	 
	private class Node<E extends Comparable<E>>{
		
		// atribut node : element bertipe E, node left, node right
		
		E element;
		Node<E> left;
		Node<E> right;
		
		// Constructor class Node
		
		public Node(E element){
			this.element = element;
			this.left = null;
			this.right = null;
		}
	}
	
	// atribut binary search tree
	
	private Node<E> root;
	private boolean condition;
	
	//constructor binary search tree
	
	public BinarySearchTree(){
		this.root = null;
	}
	
	/*
	 * method add: memasukan elemen kedalam BST
	 * @param: E element
	 * @ return node dari hasil masukan
	 * dengan bantuan add(element) sebagai driver method dan add(element, node) sebagai helper
	 */
	// driver method
	
	public void makeBST(E elemen){
		add(elemen, root);
	}
	public void add(E element){
		boolean add = add(element, root);
		if(add==true) System.out.println(element+" ditambahkan");
	}
	// helper method
	public boolean add(E elemen, Node<E> e){
		if (e == null){
			e = new Node<E>(elemen);
			if (root == null) {
				root = e;
				return true;
				}
			}
        if (elemen.compareTo(e.element)<0){
              if (e.left == null) {
                    e.left = new Node<E>(elemen);
                    return true;
              } else
                    return add(elemen, e.left);
        } else if (elemen.compareTo(e.element)>0){
              if (e.right == null) {
                    e.right = new Node<E>(elemen);
                    return true;
              } else
                    return add(elemen, e.right);
        }
        else{
           	  System.out.println("DUPLICATE");
           	  return false;
        }
  }
	
	/*
	 * method Find
	 * @param: elemen yang mau dicari
	 * @return: nood dari elemen yang dicari, jika tidak null
	 *  method driver dan helper
	 */
	
	// driver method
	public void find(E element){
		
		boolean find = find(element, root);
		if(find==false) System.out.println("TIDAK ADA");
		else System.out.println("ADA");this.condition= false;
	}
	// helper method
	private boolean  find(E element, Node<E> node){
		if(node == null){
			return false;
		}
		if(element.equals(node.element)){
			
			return true;
		}
		else if(element.compareTo(node.element)<0){
			return find(element,node.left);
		}
		else if(element.compareTo(node.element)>0){
			return find(element, node.right);
		}
		return false;
	}
	
	/*
	 * method delete
	 * @param: element yang mau dihapus
	 * @return: element yang telah dihapus
	 */
	
	// driver method
	 public void remove(E elemen) {
		 try{
	       		remove(elemen, root);
	       		System.out.println(elemen+" dihapus");
		 }catch(NullPointerException e){
			 System.out.println(elemen + " tidak ada");
		 }
	    }
	    
	    //helper method
	    private Node<E> remove(E elemen, Node<E> node) {
	        if (node == null) {
	            throw new NullPointerException();
	        } else {
	            if (elemen.compareTo(node.element) < 0) {
	                node.left = remove(elemen, node.left);
	            } else if (elemen.compareTo(node.element)> 0) {
	                node.right= remove(elemen, node.right);
	            }
	            // Kasus untuk 2 anak
	            
	            else if (node.left != null && node.right != null) {
	                node.element = findMin(node.right);
	                node.right= removeMin(node.right);
	            }
	            // Kasus untuk node punya 1 anak dan tidak punya anak
	            else {
	                node = (node.left != null) ? node.left : node.right;
	            }
	        }
	        return node;
	    }
	    
	    // untuk succesor inorder
	    public Node<E> removeMin(Node<E> node) {
	        if (node == null) {
	            throw new NullPointerException();
	        } else if (node.left == null) {
	            node = node.right;
	        } else {
	            node.left = removeMin(node.left);
	        }
	        return node;
	    }
	// mencari elemen terkecil di kanan node yang akan dihapus
	public E findMin(Node<E> node){
		E min = node.element;
		while(node.left!= null){
			min = node.left.element;
			node = node.left;
		}
		return min;
	}
	
	/*
	 * method traversal
	 * pre order, in order, post order
	 */
	
	// pre order driver method
	public void preOrder(){
		if(root!=null){
			ArrayList<E> result = new ArrayList<E>();
			preOrder(result,root);
			System.out.print("Pre-order: ");
			for(int x = 0; x<result.size();x++){
				if(x==0)System.out.print(result.get(x));
				else if(x!=result.size()-1)System.out.print(", "+result.get(x));
				else System.out.print(", "+result.get(x)+"\n");
			}
		}
	}
	// pre order helper method
	public void preOrder(ArrayList<E> e, Node<E> node){
		e.add(node.element);
		if(node.left!= null){
			preOrder(e,node.left);
		}
		if(node.right!= null){
			preOrder(e,node.right);
		}
	}
	
	// in order 
		public void inOrder(){
			if(root!=null){
				ArrayList<E> result = new ArrayList<E>();
				inOrder(result,root);
				System.out.print("In-order: ");
				for(int x = 0; x<result.size();x++){
					if(x==0)System.out.print(result.get(x));
					else if(x!=result.size()-1)System.out.print(", "+result.get(x));
					else System.out.print(", "+result.get(x)+"\n");
				}
			}
		}
		// in order helper method
		public void inOrder(ArrayList<E> e, Node<E> node){
			if(node.left!= null){
				inOrder(e,node.left);
			}
			e.add(node.element);
			if(node.right!= null){
				inOrder(e,node.right);
			}
		}
		// post  order driver method
		public void postOrder(){
			if(root!=null){
				ArrayList<E> result = new ArrayList<E>();
				postOrder(result,root);
				System.out.print("Post-order: ");
				for(int x = 0; x<result.size();x++){
					if(x==0)System.out.print(result.get(x));
					else if(x!=result.size()-1)System.out.print(", "+result.get(x));
					else System.out.print(", "+result.get(x)+"\n");
				}
			}
		}
		// post order helper method
		public void postOrder(ArrayList<E> e, Node<E> node){
			if(node.left!= null){
				postOrder(e,node.left);
			}
			if(node.right!= null){
				postOrder(e,node.right);
			}
			e.add(node.element);
		}
}