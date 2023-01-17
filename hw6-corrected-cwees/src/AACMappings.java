import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Represents the mappings for item categories that should be displayed
 * @author Chris He
 */
public class AACMappings {
    private HashMap<String, AACCategory> AACMappings = new HashMap<String, AACCategory>();
    private String currentCategory; //imageloc

    public AACMappings (String filename){
        try {
			Scanner scan = new Scanner(new File(filename));
			while(scan.hasNextLine()){
				String test = scan.nextLine();
                String splitAlpha[]=StringReader(test, " ");
                //splitalpha 0 = imageLoc, splitalpha1=text

                if(splitAlpha[0].charAt(0)!='>'){// for categories
                    this.currentCategory="";//change to home
                    add(splitAlpha[0], splitAlpha[1]); //add
                    this.currentCategory=splitAlpha[0];//change to new category
                }
                else{
                    add(splitAlpha[0], splitAlpha[1]); //add to current category
                }
			}
            this.currentCategory="";// return home after done
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    /**
     * Method splits a string into two parts depending on regex
     * @param inString String to be split
     * @param regex How to split the string
     * @return splitAlpha, an array of strings split by regex
     */
    private String[] StringReader(String inString, String regex){
        int index = inString.indexOf(regex);
        String Alpha[]={"",""};
        Alpha[0]=inString.substring(0, index);
        Alpha[1]=inString.substring(index+1, inString.length());
        return Alpha;
    }
    /**
     * Given the image location selected, it determines the associated text with the image. 
     * If the image provided is a category, it also updates the AAC's current category 
     * to be the category associated with that image
     * @param imageLoc - the location where the image is stored
     * @return returns the text associated with the current image
     */
    
    public String getText(String imageLoc){
        
        if(isCategoryHome()){
            AACCategory test = AACMappings.get(imageLoc);
            String newcategory = test.getCategory();
            this.currentCategory = imageLoc;
            return newcategory;
        }
        else{
            String image = ">"+imageLoc;
            AACCategory test = this.AACMappings.get(currentCategory);
            String text;
                text = test.getText(image);
            if(text==null){
                throw new NoSuchElementException("File not found");
            }
            return text;
        }
    }
    /**
     *  Provides an array of all the images in the current category
     * @return the array of images in the current category
     */
    public String[] getImageLocs(){
        if(isCategoryHome()){
            Set<String> set=AACMappings.keySet();
            Object[] object =  set.toArray();
            String[] test=new String[AACMappings.size()];
            for(int i = 0; i<object.length;i++){
                test[i]=object[i].toString();
            }
            return test;
        }

        else{
            AACCategory test =AACMappings.get(this.currentCategory);
            String[] images = test.getImages();
            for(int i = 0;i<images.length;i++){
                images[i]=images[i].substring(1);
            }
            return images;
        }
    }
    /**
     * Resets the current category of the AAC back to the default category
     * 
     */
    public void reset(){
        currentCategory="";
    }
    /**
     * Gets the current category
     * @return returns the current category or the empty string if on the default category
     */
    public String getCurrentCategory(){
        return currentCategory;
    }
    /**
     * helper methods that checks if current category is home or not
     * @return true if it is home, false if not
     */
    private boolean isCategoryHome(){
        if(currentCategory.equals("")){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Determines if the image represents a category or text to speak
     * @param imageLoc - the location where the image is stored
     * @return true if the image represents a category, false if the image represents text to speak
     */
    public boolean isCategory​(String imageLoc){
        if(imageLoc.charAt(0)=='>'){
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * Writes the ACC mappings stored to a file. The file is formatted as the text location of the category 
     * followed by the text name of the category and then one line per item in the category 
     * that starts with > and then has the file name and text of that image for instance: 
     * img/food/plate.png food 
     * >img/food/icons8-french-fries-96.png french fries 
     * >img/food/icons8-watermelon-96.png watermelon 
     * img/clothing/hanger.png clothing 
     * >img/clothing/collaredshirt.png collared shirt 
     * represents the file with two categories, food and clothing and food has french fries 
     * and watermelon and clothing has a collared shirt
     * @param filename - the name of the file to write the AAC mapping to
     */
    void writeToFile(String filename){

        ArrayList<String> store = new ArrayList<>();
        
        for(String imageLoc:AACMappings.keySet()){
            //writes category name
            AACCategory node = AACMappings.get(imageLoc);
            String catName = node.getCategory();
            store.add(imageLoc+" "+catName);
            //per item in category it calls getimages
            String[] images = node.getImages();
            for(int i =0; i<images.length;i++){
                store.add(images[i]+" "+node.getText(images[i]));
            }
        }
        //writes everything to arraylist then writes to file
        try {
            
			FileWriter myWriter = new FileWriter(filename);

            for(String str: store){
                myWriter.write(str);
                myWriter.write("\n");
            }
			
			myWriter.close();
		}catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
        }
        
    }

    public void add(String imageLoc, String text){
        //If it is on the home screen, it should create a new category with the given image and name.
        //If it is in a category, it should add the image and text to speak to the currently shown category

        
        if(isCategoryHome()){
            AACCategory node = new AACCategory(text);

            AACMappings.put(imageLoc, node);

        }
        else{
            if(imageLoc.charAt(0)!='>'){
                imageLoc=">"+imageLoc;
            }
            AACCategory node =this.AACMappings.get(this.currentCategory);
            node.addItem(imageLoc, text);
            AACMappings.replace(imageLoc, node);
        }
    }
}
