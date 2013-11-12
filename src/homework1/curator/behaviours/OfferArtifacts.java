package homework1.curator.behaviours;

import homework1.curator.Element;
import homework1.curator.ElementsDatabase;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * behaviour that should response to all the requests for the particular item
 * @author zikesjan
 */
public class OfferArtifacts extends CyclicBehaviour{

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if(msg != null){
            String name = msg.getContent();
            Element e = ElementsDatabase.getElement(name);
            
            ACLMessage reply = msg.createReply();                               //TODO perform sending of the element to the client
            
            if(e != null){
                try {
                    reply.setContentObject(e);                                  //XXX maybe feasable way how to send objects
                    reply.setPerformative(ACLMessage.PROPOSE);
                    
                } catch (IOException ex) {
                    Logger.getLogger(OfferArtifacts.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                reply.setContent("Desired item not found");      
                reply.setPerformative(ACLMessage.REFUSE);
            }
            myAgent.send(reply);
        }
    }
    
}
