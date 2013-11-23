package homework.curator;

import java.io.Serializable;

/**
 * Representation of the one particular art element in the museum or galery
 *
 * @author zikesjan
 */
public class Element implements Serializable {

    private String name;
    private String type;
    private String author;
    private int completationYear;

    public Element() {
    }

    public Element(String name, String author, int completationYear, String type) {
        this.name = name;
        this.author = author;
        this.completationYear = completationYear;
        this.type = type;
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

    public int getCompletationYear() {
        return completationYear;
    }

    public void setCompletationYear(int completationYear) {
        this.completationYear = completationYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
