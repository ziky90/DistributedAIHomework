package homework1.curator.behaviours;

import homework1.curator.Element;
import homework1.curator.ElementsDatabase;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

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
            
            ACLMessage reply = msg.createReply();   //TODO perform sending of the element to the client
            
            if(e != null){
                //TODO send
            }else{
                //TODO send that element was not found
            }
        }
    }
    
}
