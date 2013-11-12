package homework1.curator.behaviours;

import homework1.curator.ElementsDatabase;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Beahaviour that recieves requests for catalog from TourGuide agent and returns the catalog
 * @author zikesjan
 */
public class OfferCatalog extends CyclicBehaviour{

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if(msg != null){
            System.out.println("<"+myAgent.getLocalName()+">:: message catalog request recieved");
            String[] catalog = ElementsDatabase.getCatalog();
            
            ACLMessage reply = msg.createReply();                               
            try {
                reply.setContentObject(catalog);
                reply.setPerformative(ACLMessage.PROPOSE);
            } catch (IOException ex) {
                Logger.getLogger(OfferCatalog.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            myAgent.send(reply);
            System.out.println("<"+myAgent.getLocalName()+">:: catalog sent in the reply");
        }
    }
    
}
