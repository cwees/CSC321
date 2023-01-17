//DON"T USE == or notequals for strings

//category is the smaller one within mapping
import java.util.HashMap;
import java.util.Set;
/**
 * Represents the mappings for a single page of items that should be displayed
 * @author Chris He
 */
public class AACCategory {
    private String name;
    HashMap<String, String> AACCategory = new HashMap<String, String>();
    //should be a nested hashmap

    /**
     * Creates a new empty category with the given name
     * @param name the name of the category
     */
    public AACCategory(String name) {
        this.name=name;
    }
    /**
     * Adds item to Hashmap
     * @param imageLoc where the image is located
     * @param text what text should be displayed
     */
    public void addItem(String imageLoc, String text){
        AACCategory.put(imageLoc, text);
    }
    /**
     * 
     * @return name of category
     */
    public String getCategory(){
        return this.name;
    }

    /**
     * Returns an array of all the images in the category
     * @return an array of all images in category
     */
    public String[] getImages(){

        // String[] stringarray= new String[AACCategory.size()];
    
        // int i = 0;
        // for (String key: AACCategory.keySet()) {
        //     stringarray[i]=AACCategory.get(key);
        //     i++;
        // }
        Set<String> set=AACCategory.keySet();
        Object[] object =  set.toArray();
        String[] test=new String[AACCategory.size()];
        for(int j = 0; j<object.length;j++){
            test[j]=object[j].toString();
        }
        return test;
    }
    public String getText(String imageLoc){
        String text= AACCategory.get(imageLoc);
        return text;
    }
    public Boolean hasImage​(String imageLoc){
        return AACCategory.containsKey(imageLoc);
    }


}
