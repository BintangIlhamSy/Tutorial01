/*
 * Lab 5B 
 * Bintang Ilham Syahputra, SI 2016
 */

import java.util.*;
import java.io.*;
public class SDA1718L5B_Selasa {

	public static void main(String[] args)throws IOException {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String[] command;
		
		command = input.readLine().split(" ");
		String namaHero = command[0];
		int stamina = Integer.parseInt(command[1]);
		
		Pahlawan bintang = new Pahlawan(namaHero, stamina,1,0);
		
		System.out.printf("Welcome to Mobel Legenda! Hero: %s, Stamina awal: %d\n", bintang.nama, bintang.stamina);
		
		command = input.readLine().split(" ");
		int banyakKota = Integer.parseInt(command[0]);
		int banyakPerintah = Integer.parseInt(command[1]);
		
		BTSMobel tree = new BTSMobel();
		
		for(int x = 0;x<banyakKota;x++){
			command = input.readLine().split(" ");
			Kota baru = new Kota(command[0],Integer.parseInt(command[1]),Integer.parseInt(command[2]));
			tree.insert(baru);
		}
		
		for(int x = 0;x<banyakPerintah;x++){
			command = input.readLine().split(" ");
			
			switch(command[0].toUpperCase()){
			
			case "BUILD":
				Kota newCity = new Kota(command[1], Integer.parseInt(command[2]),Integer.parseInt(command[3]));
				tree.insert(newCity);
				System.out.print(command[1]+" dengan kekuatan "+command[2]+" dibangun\n");
				break;
				
			case "INSPECT":
				String namaKota = command[1];
				if(tree.find(namaKota)!=null){
					Kota target = tree.find(namaKota);
					System.out.print(target.nama+". Kekuatan: "+target.powerKota+". Req. Stamina: "+target.stamina+"\n");
				}
				else{
					System.out.print(namaKota+" tidak ada\n");
				}
				break;
			case "DEMOLISH":
				String nameKota = command[1];
				try{
					tree.remove(nameKota);
					System.out.print(nameKota+" dihancurkan\n");
				}
				catch(NullPointerException e){
					System.out.print(nameKota+" tidak ada\n");
				}
				break;
			case "WORLD":
				System.out.print("List kota:\n");
				tree.inOrder();
				
				for(Kota kota : tree.DataIn){
					System.out.print("- "+kota.nama+"\n");
				}
				tree.resetDataIn();
				break;
			case "EXPLORE":
				bintang.explore(tree);
				break;
			}
		}
	}
}

/*
 * class Pahlawan
 * representasi dari tokoh game
 */

class Pahlawan{
	public String nama;
	public int stamina;
	public int staminafull;
	public int level;
	public int exp;
	public int levelUp;
	
	public Pahlawan(String nama, int stamina, int level, int exp){
		super();
		this.nama = nama;
		this.stamina = stamina;
		this.staminafull= stamina;
		this.level = level;
		this.exp = exp;
		this.levelUp = 1000;
		
	}
	// method untuk mengurangi stamina
	public void minStamina(int stamina) {
        this.stamina -= stamina;
    }
	
	// menaikkan level hero
	 public void levelUp() {
         this.level++;
         this.staminafull+= 200;
         this.levelUp += 1000;
     }
	 
	  public void addExp(int exp) {
          this.exp += exp;
      }
	  
	  // method explore
	  
	  public void explore(BTSMobel tree) {
          tree.preOrder();
          for (int i = 0; i < tree.DataPre.size(); i++) {
              if (tree.DataPre.get(i).stamina <= this.stamina){
                  this.minStamina(tree.DataPre.get(i).stamina);
                  this.addExp(tree.DataPre.get(i).powerKota);
                  System.out.printf("%s membersihkan kota %s. Sisa stamina: %d. Exp: %d\n", this.nama, tree.DataPre.get(i).nama, this.stamina, this.exp);
                  if ((this.exp / this.levelUp) > 0) {
                      this.levelUp();
                      System.out.printf("LEVEL UP! %s level %d. Stamina maksimal: %d\n", this.nama, this.level, this.staminafull);
                  }
              } else {
                  System.out.println(tree.DataPre.get(i).nama + " tidak dibersihkan");
              }
          }
          this.stamina = this.levelUp;
          tree.resetDataPre();
      }
  }

/*
 * Class Kota: representasi dari node dalam binary search tree
 * atribut powerKota, nama, stamina
 */
class Kota implements Comparable<Kota>{
	public String nama;
	public int powerKota;
	public int stamina;
	public Kota kiri;
	public Kota kanan;
	
	public Kota(String nama, int kekuatan, int stamina){
		this.nama = nama;
		this.powerKota = kekuatan;
		this.stamina = stamina;
		// insiasi kota tetangga
		this.kiri = null;
		this.kanan = null;
	}

	@Override
	public int compareTo(Kota o) {
		if(this.nama.compareTo(o.nama)<0)
			return -1;
		else if(this.nama.compareTo(o.nama)>0)
			return 1;
		else
			return 0;
	}
}
/*
 * Class BSTMobel
 * representasi dari binary search tree yang berisi node
 * atribut berupa kumpulan dari kota kota
 */

class BTSMobel{
	
	private Kota root;
	public ArrayList<Kota> DataPre;// meyimpan hasil preorder
	public ArrayList<Kota> DataIn;// menyimpan hasil inorder
	
	// constructor tree
	
	public BTSMobel(){
		this.root = null;
		this.DataIn = new ArrayList<>();
		this.DataPre = new ArrayList<>();
	}
	public BTSMobel(Kota root){
		this.root = root;
		this.DataIn = new ArrayList<>();
		this.DataPre = new ArrayList<>();
	}
	
	/*
	 * method insert kota
	 * @param : Kota class
	 * @return : Node kota;
	 */
	
	   public void insert(Kota kotaBaru) {
           insert(kotaBaru, root);
       }
	   
	private Kota insert(Kota kotaBaru, Kota node) {
        if (node == null) {
            node = kotaBaru;
            if (root == null) {
                root = node;
            }
        } else if (kotaBaru.nama.compareTo(node.nama)< 0) {
            node.kiri = insert(kotaBaru, node.kiri);
        } else if (kotaBaru.nama.compareTo(node.nama) > 0) {
            node.kanan= insert(kotaBaru, node.kanan);
        }
        return node;
    }
	
	/*
	 *method find kota
	 *@param : string nama kota
	 *@return : node
	 */
	
	// driver method
	public Kota find(String namaKota) {
        return find(namaKota, root);
    }
	
	// helper method
    private Kota find(String namaKota, Kota node) {
        if (node == null) {
            return null;
        }
        if (node.nama.equals(namaKota)) {
            return node;
        } else if (namaKota.compareTo(node.nama) < 0) {
            return find(namaKota, node.kiri);
        } else if (namaKota.compareTo(node.nama) > 0) {
            return find(namaKota, node.kanan);
        }
        return null;
    }

    private Kota findMin(Kota node) {
        if (node == null) {
            throw new NullPointerException();
        } else if (node.kiri == null) {
            return node;
        } else {
            return findMin(node.kiri);
        }
    }
    
    /*
     * method remove
     * @param : nama kota
     * @return: node
     */
    
    // driver method
    public Kota remove(String namaKota) {
        return remove(namaKota, root);
    }
    
    //helper method
    private Kota remove(String namaKota, Kota node) {
        if (node == null) {
            throw new NullPointerException();
        } else {
            if (namaKota.compareTo(node.nama) < 0) {
                node.kiri = remove(namaKota, node.kiri);
            } else if (namaKota.compareTo(node.nama) > 0) {
                node.kanan= remove(namaKota, node.kanan);
            }
            // Kasus untuk 2 anak
            
            else if (node.kiri != null && node.kanan != null) {
                node.nama = findMin(node.kanan).nama;
                node.kanan= removeMin(node.kanan);
            }
            // Kasus untuk node punya 1 anak dan tidak punya anak
            else {
                node = (node.kiri != null) ? node.kiri : node.kanan;
            }
        }
        return node;
    }
    
    // untuk succesor inorder
    public Kota removeMin(Kota node) {
        if (node == null) {
            throw new NullPointerException();
        } else if (node.kiri == null) {
            node = node.kanan;
        } else {
            node.kiri = removeMin(node.kiri);
        }
        return node;
    }
    
    /*
     * method inorder dan postorder
     */
    

    public void inOrder() {
        if (root != null) {
            inOrder(root);
        }
    }

    public void inOrder(Kota node) {
        if (node.kiri != null) {
            inOrder(node.kiri);
        }
        DataIn.add(node);
        if (node.kanan != null) {
            inOrder(node.kanan);
        }
    }

    public void preOrder() {
        if (root != null) {
            preOrder(root);
        }
    }

    public void preOrder(Kota node) {
        DataPre.add(node);
        if (node.kiri != null) {
            preOrder(node.kiri);
        }
        if (node.kanan != null) {
            preOrder(node.kanan);
        }
    }
    public void resetDataPre() {
        this.DataPre.clear();
    }
    public void resetDataIn() {
        this.DataIn.clear();
    }
}