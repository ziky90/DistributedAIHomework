package homework1.curator;

import java.io.Serializable;

/**
 * Representation of the one particular art element
 * @author zikesjan
 */
public class Element implements Serializable{
    
    private String name;
    
    private String author;
    
    private String completationYear;

    public Element(){
        
    }

    public Element(String name, String author, String completationYear) {
        this.name = name;
        this.author = author;
        this.completationYear = completationYear;
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompletationYear() {
        return completationYear;
    }

    public void setCompletationYear(String completationYear) {
        this.completationYear = completationYear;
    }
    
    
    
}
