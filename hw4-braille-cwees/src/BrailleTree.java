import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;


//write getbraille

/**
 * This class stores braille characters in a binary tree. 
 * @author Chris He
 *
 */
public class BrailleTree {
	private BrailleNode root;

	/**
	 * Creates the Braille tree from the alphabet in the provided file.
	 * The file should be formated, one braille character per line, with
	 * first the braille encoding as a series of six bits representing the
	 * six dots in a top to bottom, left to right order. Then after the encoding
	 * there will be a space followed by the text that braille character
	 * represents. In addition, it adds the all 0 encoding (000000 for six dot braille)
	 * as a space.
	 * @param filename the name of the file that stores the encoding mapping
	 */
	public BrailleTree(String filename) {
		add("000000"," ");
		try {
			
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String test = scan.nextLine();
				String splitAlpha[]=test.split(" ");
				add(splitAlpha[0],splitAlpha[1]);
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the braille character with the provided binary 
	 * encoding to the tree with the provided text. If there is
	 * already something with that encoding in the tree, it replaces
	 * that text. 
	 * @param binary the braille encoding of the character
	 * @param text the text that the character represents
	 */
	public void add(String binary, String text) {
		this.root=this.add(binary, text, this.root);
	}
	/**
	 * Adds the braille character with the provided binary 
	 * encoding to the tree with the provided text. If there is
	 * already something with that encoding in the tree, it replaces
	 * that text. 
	 * @param binary the braille character that the text is associated with
	 * @param text the text to add to the tree
	 * @param curr the subtree to add the text to
	 * @return the node that is the root of the subtree
	 */
	private BrailleNode add(String binary, String text, BrailleNode curr) {

		//create path of binary node
		//think its w orking now
		if(binary.isEmpty()==false){//change while loop to if? need to find a way to break out of loop
			char firstNumb=binary.charAt(0);
			if(curr==null){
				curr=new BrailleNode("");
			}
			if(firstNumb=='0'){	
				curr.setLeft(this.add(binary.substring(1),text, curr.getLeft()));
			}
			else{
				curr.setRight(this.add(binary.substring(1),text, curr.getRight()));
			}

		}
		//add node at that specific path
		if(binary==""){//currently sets text but then infinitely loops the while statement.	
			curr=new BrailleNode(text);
		}
		return curr;
		
	}

	/**
	 * Gets the text associated with the provided braille character
	 * @param binary the binary encoding of the braille character
	 * @return the text associated with the braille character or
	 * the empty string if there is no such encoding in the tree
	 */
	public String getText(String binary) {
		String string= this.getText(binary, this.root);
		return string;
	}
	
	/**
	 * Gets the text associated with the provided braille character
	 * @param binary the binary encoding of the braille character
	 * @return the text associated with the braille character or the
	 * empty string if there is no such encoding in the tree
	 */
	private String getText(String binary, BrailleNode curr) {
		String test="";
		if(curr==null){
			return "";
		}
		if(binary.isEmpty()==false){
			char firstNumb=binary.charAt(0);
			if(firstNumb=='0'){
				test=getText(binary.substring(1),curr.getLeft());
				
			}
			else{
				test=getText(binary.substring(1),curr.getRight());
			}
		}
		else if(binary==""){
			test= curr.getText();
		}
		return test;
		
	}
	
	/**
	 * Finds and returns the braille encoding for the provided text
	 * @param text the text to find the braille encoding of
	 * @return the binary braille encoding that has that text or 
	 * the empty string if that text is not in the tree.
	 */
	public String getBraille(String text) {
		String braille="";
		
		for(int i =0; i<text.length();i++){
			String character="";
			character=character+text.charAt(i);
			String response =this.getBraille(character,this.root, "" );
			braille=braille+response;
		}
		
		return braille;
		//public accessor method, breaks up the text into individual characters
	}
	
	/**
	 * Finds and returns the braille encoding for the provided text
	 * @param text the text to find the braille encoding of
	 * @param curr the current node that you are checking
	 * @param path the binary encoding the represents the path 
	 * to the current node 
	 * @return the binary braille encoding that has that text or 
	 * the empty string if that text is not in the tree.
	 */
	private String getBraille(String text, BrailleNode curr, String path) {
		if(curr==null){ //if empty
			return "";
		}

		if(curr.getLeft().getText()==text){//base case 1 where searched for letter is left child of curr
			return path+"0";
		}
		else if(curr.getRight().getText()==text){ //base case 2 where searched for letter is right child of curr
			return path+"1";
		}
		else if(curr.getLeft()==null){ //base case 3 where you're at the end of the tree and letter has not been found
			return "";
		}
		else{
			return getBraille(text,curr.getLeft(),path+"0")+getBraille(text,curr.getRight(),path+"1"); //recursion
		}
		//change base case 1 and 2  to the base case where curr is what is being looked for and returns full path. 
		//no need for 3 total base cases
				
	}
	
	/**
	 * Given a file written in braille, it translates it to the
	 * text provided in the tree and writes it to a new file, outfile.
	 * @param infile the braille file to translate
	 * @param outfile the file to write the translation to
	 */
	public void translateFile(String infile, String outfile) {
		try {
			FileWriter myWriter = new FileWriter(outfile);
			File Obj = new File(infile);
			Scanner Reader = new Scanner(Obj);
			while (Reader.hasNextLine()) {
				String data = Reader.nextLine();
				String translated=StringSplitter(data);
				myWriter.write(translated);
				myWriter.write("\n");
			}
			Reader.close();
			myWriter.close();
		}catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	/**
	 * Splits longString into 6 digit braille segments and places them 
	 * into an arraylist. It then takes the arraylist of braille and 
	 * converts them into English using getText and concatenates it into one string.
	 * @param longString string of Braille to be converted
	 * @return translated, English translated form of longString
	 */
	private String StringSplitter(String longString){
		String translated="";
		ArrayList<String> store= new ArrayList<String>();
		store.clear();
		String binary="";
		int count = 0;
		for(int i =0;i<longString.length();i++){
			binary=binary+longString.charAt(i);
			count++;
			if(count==6){
				store.add(binary);
				binary="";
				count=0;
			}
			
		}
		for(int i = 0; i<store.size();i++){
			translated=translated+getText(store.get(i));
		}
		return translated;
	}
	
	/**
	 * Class representing a node in a BrailleTree. Nodes without
	 * associated text (e.g. inner nodes) should store the empty string
	 * @author Catie Baker
	 */
	private class BrailleNode {
		private String text;
		private BrailleNode left;
		private BrailleNode right;
		
				
		/**
		 * Creates a BrailleNode with the provided text value
		 * @param data the text to store in the node
		 */
		public BrailleNode(String data) {
			this.text = data;
			this.right = null;
			this.left = null;
		}

		/**
		 * Gets the text stored in the node
		 * @return the text stored in the node
		 */
		public String getText() {
			return text;
		}

		/**
		 * Sets the text stored in the node
		 * @param data the text to store in the node
		 */
		public void setText(String data) {
			this.text = data;
		}

		/**
		 * Gets the left child of the node
		 * @return the left child of the node
		 */
		public BrailleNode getLeft() {
			return left;
		}
		
		/**
		 * Sets the left child of the node
		 */
		public void setLeft(BrailleNode left) {
			this.left = left;
		}

		/**
		 * Gets the right child of the node
		 * @return the right child of the node
		 */
		public BrailleNode getRight() {
			return right;
		}

		/**
		 * Sets the right child of the node
		 */
		public void setRight(BrailleNode right) {
			this.right = right;
		}
		
		
	}
}
