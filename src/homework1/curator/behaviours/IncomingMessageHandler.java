package homework1.curator.behaviours;

import homework1.curator.Element;
import homework1.curator.ElementsDatabase;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * behaviour that handles all the incoming messages to the CuratorAgent and that
 * sends the responses to all the requests for the particular items or catalogs
 *
 * @author zikesjan
 */
public class IncomingMessageHandler extends SimpleAchieveREResponder {

    private static final long serialVersionUID = 1L;

    public IncomingMessageHandler(Agent a, MessageTemplate msg) {
        super(a, msg);
    }

    /**
     * Diversing the type of the message basedon the incoming string and then
     * handlig it and replying
     *
     */
    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) {
        ACLMessage reply = request.createReply();
        String name = request.getContent();
        if (name.equals("")) {
            System.out.println("<" + myAgent.getLocalName() + ">:: message catalog request recieved");
            String[] catalog = ElementsDatabase.getCatalog();
            try {
                reply.setContentObject(catalog);
            } catch (IOException ex) {
                Logger.getLogger(IncomingMessageHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            reply.setPerformative(ACLMessage.INFORM);

            System.out.println("<" + myAgent.getLocalName() + ">:: catalog sent in the reply");
        } else {
            System.out.println("<" + myAgent.getLocalName() + ">:: request for element " + name);
            Element e = ElementsDatabase.getElement(name);
            if (e != null) {
                try {
                    reply.setContentObject(e);
                    reply.setPerformative(ACLMessage.INFORM);

                } catch (IOException ex) {
                    Logger.getLogger(IncomingMessageHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("<" + myAgent.getLocalName() + ">:: Desired item not found");
                reply.setContent("Desired item not found");
                reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
            }
            myAgent.send(reply);
            System.out.println("<" + myAgent.getLocalName() + ">:: elements " + name + " details sent");
        }
        return reply;
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
        return super.prepareResponse(request);
    }
}
