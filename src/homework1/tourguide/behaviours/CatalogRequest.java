package homework1.tourguide.behaviours;

import homework1.curator.Element;
import homework1.profiler.behaviours.GetDetailsBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREInitiator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behaviour that requests for the catalog from the curator
 * @author zikesjan
 */
public class CatalogRequest extends SimpleAchieveREInitiator{
    
    @Override
    protected ACLMessage prepareRequest(ACLMessage msg){
        msg.setContent("");
        System.out.println("<"+myAgent.getLocalName()+">: sending reques for the catalog to the museum");
        return super.prepareRequest(msg);
    }
    
    public CatalogRequest(Agent a,  ACLMessage am){
        super(a, am);
    }
    
    @Override
    protected void handleInform(ACLMessage msg) {
        String[] catalog = null;
        try {
            catalog = (String[]) msg.getContentObject();
        } catch (UnreadableException ex) {
            System.out.println("<"+myAgent.getLocalName()+">: not element found in the message");
            Logger.getLogger(GetDetailsBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("<"+myAgent.getLocalName()+">: recieved catalog "+catalog[0]+", "+catalog[1]);
       
    }

    @Override
    protected void handleNotUnderstood(ACLMessage msg) {
        System.out.println("<"+myAgent.getLocalName()+">: recieved unknown reply "+msg.getContent());
    }
}
