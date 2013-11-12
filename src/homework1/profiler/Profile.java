package homework1.profiler;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Object that holds the informations about the user
 * @author zikesjan
 */
public class Profile implements Serializable{
    private String name;
    
    private int age;
    
    private String ocupancy;
    
    private ArrayList<String> interests;
    
    public Profile(){
    }
    
    public Profile(String name){
        this.name = name;
    }

    public Profile(String name, int age, String ocupancy, ArrayList<String> interests) {
        this.name = name;
        this.age = age;
        this.ocupancy = ocupancy;
        this.interests = interests;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOcupancy() {
        return ocupancy;
    }

    public void setOcupancy(String ocupancy) {
        this.ocupancy = ocupancy;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }
    
    
}
