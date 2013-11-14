package homework1.profiler.behaviours;

import homework1.curator.Element;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREInitiator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Information behavior for the profiler agent to ask for the detailed
 * informations to the curator agent
 *
 * @author zikesjan
 */
public class GetDetailsBehaviour extends SimpleAchieveREInitiator {

    private String message;
    
    @Override
    protected ACLMessage prepareRequest(ACLMessage msg){
        msg.setContent(message);
        System.out.println("<"+myAgent.getLocalName()+">: sending message with request about "+message);
        return super.prepareRequest(msg);
    }
    
    public GetDetailsBehaviour(Agent a, ACLMessage am, String item) {
        super(a, am);
        this.message = item;
    }

    @Override
    protected void handleInform(ACLMessage msg) {
        Element e = null;
        try {
            e = (Element) msg.getContentObject();
        } catch (UnreadableException ex) {
            System.out.println("<"+myAgent.getLocalName()+">: not element found in the message");
            Logger.getLogger(GetDetailsBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("<"+myAgent.getLocalName()+">: recieved reply "+e.getName()+", "+e.getAuthor()+", "+e.getType());
       
    }

    @Override
    protected void handleNotUnderstood(ACLMessage msg) {
        System.out.println("<"+myAgent.getLocalName()+">: recieved unknown reply "+msg.getContent());
    }
}
