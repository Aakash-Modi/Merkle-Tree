package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Merkle {
	
	String[] word1;//array of words of file 1
	String[] word2;//array of words of file 2
	String[][] root1;//nested array of hash values of file1
	String[][] root2;//nested array of hash values of file2
	int k;
	
	//constructor
	public Merkle(String f1, String f2) {
		word1 = wordList(f1);
		word2 = wordList(f2);
		root1 = tree(word1);
		root2 = tree(word2);
		k = 0;
	}

	//pre-processing the content of file into array of words
	public String[] wordList(String filename) {
		String strLine = "";
        String str_data = "";
        int c = 0;
                
        //read file
        try {BufferedReader br = new BufferedReader(new FileReader(filename));
             while (strLine != null){
            	strLine = strLine.replaceAll("[^A-Za-z]", " ");
            	
            	c++;
            	
                str_data += strLine + "";
                strLine = br.readLine();
                
                if(c % 2000 == 0) System.out.println(c +" lines...");
                }
             br.close();}
        
        catch (FileNotFoundException e) {System.err.println("File not found");}
        catch (Exception e) {System.err.println(e.getMessage());}
                
    	String[] a = str_data.split("\\s+");
    	
    	return a;
	}
	
	//compute levels of tree
		public int level(String[] a) {
			int n = a.length;
			return (int)Math.ceil((Math.log(n) / Math.log(2)));
		}
	
	//hash computation
	public String[] hash(String[] a) {
		int len = a.length;
				
		//if words are odd in number
		if (len%2 != 0 && len != 1) {
			len = len+1;
		}	
		
		int[] hashval = new int[len];
		String[] hash = new String[len];
		
		//duplicating the last element when the length is odd
		if (a.length%2 != 0 && a.length != 1) {
			hashval[len-1] = a[a.length-1].hashCode();
			hash[len-1] = Integer.toHexString(hashval[len-1]);
		}
		
		for (int i = 0; i < a.length; i++) {
			hashval[i] = a[i].hashCode();
		
			hash[i] = Integer.toHexString(hashval[i]);}
		
		return hash;
	}	
	
	//tree computation
	public String[][] tree(String[] a) {
		int m = 0;
		String[] x = hash(a);		
		
		String[][] z = new String[level(a)+1][];//for storing the whole tree
		
		while (x.length != 1) {
			
			String[] y = new String[x.length/2];
			
			z[level(a)-m] = new String[x.length];
			
			for (int j=0; j<x.length; j++) {
				z[level(a)-m][j] = x[j];
			}
				
			for (int i=0; i < (x.length/2); i++) {
				y[i] = x[i*2].concat(x[(i*2)+1]);
			}
				
			x = hash(y);
			m++;
		}
		
		//storing the root of tree
		z[0] = new String[x.length];
		z[0][0] = x[0];
						
		return z;		
	}
	
	//to find the incorrect words (recursive function)
	public void find(int i, int j, int l) {
				
		if (i<(l+1)) {
			if (j < root1[i].length) {
				if (!(root1[i][j].equals(root2[i][j]))) {
				
					//left leaf
					find(i+1, 2*j, l);	
				
					//right leaf
					find(i+1, ((2*j)+1), l);
								
					if (!(root1[i][j].equals(root2[i][j])) && i==l) {
						if (j != word1.length) {
							System.out.println(word1[j]+" , "+word2[j]+" at index "+j);
							k++;
						}
					}
				}
			}
		}		
	}
	
	//compare two files
	public void compare() {
		
		if (!(root1[0][0].equals(root2[0][0]))) {
			find(0,0,level(word1));
			System.out.println("The above mentioned words are different. Hence, the file is tampered.");
		}	
	}
	
	//to print Merkle tree
	public void printTree(String[][] a) {
		for (int i =0; i<a.length; i++) {
			for (int j = 0; j<a[i].length; j++) {
				System.out.print(a[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	//print function
	public void print() {
		//String[] root = root(word1,word2);
		System.out.println("Root of file 1: "+root1[0][0]);
		System.out.println("Root of file 2: "+root2[0][0]+"\n");
		
		System.out.println("Tree of file 1:");
		printTree(root1);
		System.out.println("");
		
		System.out.println("Tree of file 2:");
		printTree(root2);
		System.out.println("");
		
		if (word1.length == word2.length) {
			compare();
			System.out.println("\n"+ ((float)(word1.length-k)/word1.length)*100 +" % content of files are similar.");
		}
		else System.out.println("Files are completely different because the length of files don't match.");
		
	}	
	
	public static void main(String[] args) {
		//files with same contents
		//Merkle m = new Merkle("Sample-5-KB.txt","sample_5_KB.txt");
		//files with 2 words different
		Merkle m = new Merkle("test.txt","testt.txt");
		//12 mb files with 4 words different from total 9,52,381 words
		//Merkle m = new Merkle("test3.txt","test3t.txt");
		m.print();		
	}

}
