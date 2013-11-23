package homework.tourguide.behaviours;

import homework.profiler.behaviours.GetDetailsBehaviour;
import homework.tourguide.TourGuideAgent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREInitiator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behaviour that requests for the catalog from the curator
 *
 * @author zikesjan
 */
public class CatalogRequest extends SimpleAchieveREInitiator {

    private TourGuideAgent tga;

    @Override
    protected ACLMessage prepareRequest(ACLMessage msg) {
        msg.setContent("");
        System.out.println("<" + myAgent.getLocalName() + ">: sending reques for the catalog to the museum");
        return super.prepareRequest(msg);
    }

    public CatalogRequest(TourGuideAgent a, ACLMessage am) {
        super(a, am);
        this.tga = a;
    }

    @Override
    protected void handleInform(ACLMessage msg) {
        String[] catalog = null;
        try {
            catalog = (String[]) msg.getContentObject();
        } catch (UnreadableException ex) {
            System.out.println("<" + myAgent.getLocalName() + ">: not element found in the message");
            Logger.getLogger(GetDetailsBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        tga.catalog.put(msg.getSender(), catalog);
        System.out.println("<" + myAgent.getLocalName() + ">: recieved catalog " + catalog[0] + ", " + catalog[1]);
    }

    @Override
    protected void handleNotUnderstood(ACLMessage msg) {
        System.out.println("<" + myAgent.getLocalName() + ">: recieved unknown reply " + msg.getContent());
    }
}
