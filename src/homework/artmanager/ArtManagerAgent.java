package homework.artmanager;

import homework.artmanager.behaviours.GeneralBehaviour;
import homework.curator.Element;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.JADEAgentManagement.QueryAgentsOnLocation;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import java.util.ArrayList;

/**
 * Agent that represents art manager that makes Auctions with goal to sell stuff
 *
 * @author zikesjan
 */
public class ArtManagerAgent extends Agent {

    public int currentPrice = 130;
    public boolean finnish = false;
    public ArrayList<AID> museums = new ArrayList<AID>();
    public AID winner = null;

    @Override
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(MobilityOntology.getInstance());
        findMuseums();
        Element e = new Element("David", "Michelangelo", 1367, "statue");
        sendStartAuctionMessage(e);
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        addBehaviour(new GeneralBehaviour(this, msg));
    }

    /**
     * method that searches for all the museums and save them
     */
    private void findMuseums() {
        Location myLocation = here();
        ACLMessage query = prepareRequestToAMS(myLocation);
        send(query);
        MessageTemplate mt = MessageTemplate.MatchSender(getAMS());
        ACLMessage response = blockingReceive(mt);
        List residents = parseAMSResponse(response);
        for (Iterator it = residents.iterator(); it.hasNext();) {
            AID r = (AID) it.next();
            if(!r.getLocalName().equals(getLocalName())){
                museums.add(r);
            }
        }
        
        System.out.println("<" + getLocalName() + ">: found " + museums.size() + " museums");
    }

    private ACLMessage prepareRequestToAMS(Location where) {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(getAMS());
        new SLCodec().getName();
        request.setLanguage(new SLCodec().getName());
        request.setOntology(MobilityOntology.getInstance().getName());
        Action act = new Action();
        act.setActor(getAMS());
        QueryAgentsOnLocation action = new QueryAgentsOnLocation();
        action.setLocation(where);
        act.setAction(action);
        try {
            getContentManager().fillContent(request, act);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    private List parseAMSResponse(ACLMessage response) {
        Result results = null;
        try {
            results = (Result) getContentManager().extractContent(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results.getItems();

    }

    /**
     * method that runs the auction
     */
    private void sendStartAuctionMessage(Element e) {
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg = addReceivers(msg);
            msg.setContentObject(e);
            this.send(msg);
            System.out.println("<" + getLocalName() + ">: sent start auction message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method that add all the museums to the recievers of the message
     *
     * @param msg
     * @return
     */
    public ACLMessage addReceivers(ACLMessage msg) {
        for (AID aid : museums) {
            msg.addReceiver(aid);
        }
        return msg;
    }
}
