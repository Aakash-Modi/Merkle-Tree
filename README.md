# Merkle-Tree

Merkle tree implementation to identify whether the contents in two text files are similar or not, & if different output the dissimilar words in the file. It can be used to check any instance of tampering to the file under review.
It is implemented using the concept of hashing.
 
*txt* folder contains different text files used as input.
 
**Sample Input (dissimilar words in *italics*)**  
*File 1*  
This time I *really* liked your *intent* abc.  
 
*File 2*  
This time I *real* liked your *inte* abc.  
 
**Sample Output**  
Root of file 1: 8c43a85d  
Root of file 2: 380f49ce  

Tree of file 1:  
8c43a85d   
d99b2d88 5c08c747   
e8baabd8 bc59bafc 2fd186c aa084230   
27c2be 3652cd 49 c84577ab 62343ad 38b033 b971369c 17862   

Tree of file 2:  
380f49ce   
8c01b813 d29f972c   
e8baabd8 3b0b4917 2fd186c ee2973af   
27c2be 3652cd 49 35599e 62343ad 38b033 316656 17862   

really , real at index 3  
intent , inte at index 6  
The above mentioned words are different. Hence, the file is tampered.  
  
75.0 % content of files are similar.  

