package homework1.curator;

import java.util.HashMap;

/**
 * class that simulates the database of the curator agent
 *
 * @author zikesjan
 */
public class ElementsDatabase {

    private static HashMap<String, Element> data;

    /**
     * for the show case there is artificialy created the inventory based on the
     * parameter
     *
     * @param situation int (0,2)
     */
    public static void createDatabase(int situation) {
        data = new HashMap<String, Element>();

        switch (situation) {
            case 0: {
                Element e = new Element("Mona Lisa", "DaVinci", 1432, "painting");
                data.put(e.getName(), e);
                e = new Element("Guernica", "Picasso", 1965, "painting");
                data.put(e.getName(), e);
                e = new Element("Venus", "Michelangelo", 1398, "sculpture");
                data.put(e.getName(), e);
                break;
            }
            case 1: {
                Element e = new Element("Blue Flame", "mercedes", 1984, "car");
                data.put(e.getName(), e);
                e = new Element("Concorde", "EADS", 1980, "airplane");
                data.put(e.getName(), e);
                e = new Element("BMW boxer", "BMW", 1995, "motorcycle");
                data.put(e.getName(), e);
                break;
            }
            case 2: {
                Element e = new Element("Vinil record ABBA", "ABBA", 1982, "music");
                data.put(e.getName(), e);
                e = new Element("Guitar of John Lennon", "vivaldi.ltd", 1961, "musical instrument");
                data.put(e.getName(), e);
                e = new Element("Clothes of ABBA", "H&M", 1988, "clothes");
                data.put(e.getName(), e);
                break;
            }
        }

    }

    public static Element getElement(String s) {
        return data.get(s);
    }

    public static String[] getCatalog() {
        String[] catalog = new String[data.keySet().size()];
        catalog = data.keySet().toArray(catalog);
        return catalog;
    }
}
