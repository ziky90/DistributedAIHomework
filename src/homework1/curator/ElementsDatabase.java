package homework1.curator;

import java.util.HashMap;
import java.util.Set;

/**
 * class that simulates the database of the curator agent
 * @author zikesjan
 */
public class ElementsDatabase {
    
    private static HashMap<String,Element> data;
    
    public static void addElement(Element e){
        if(data == null){
            data = new HashMap<String, Element>();
        }
        data.put(e.getName(), e);
    }
    
    public static Element getElement(String s){
        return data.get(s);
    }
    
    public static String[] getCatalog(){
        String[] catalog = new String[data.keySet().size()];
        catalog = data.keySet().toArray(catalog);
        return catalog;
    }
    
    
}
