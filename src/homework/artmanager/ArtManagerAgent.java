package homework.artmanager;

import homework.artmanager.behaviours.GeneralBehaviour;
import homework.curator.Element;
import homework.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Agent that represents art manager that makes Auctions with goal to sell stuff
 * @author zikesjan
 */
public class ArtManagerAgent extends Agent {

    public int currentPrice = 130;
    public boolean finnish = false;
    public ArrayList<AID> museums = new ArrayList<AID>();
    public AID winner = null;

    @Override
    protected void setup() {
        findMuseums();
        Element e = new Element("David", "Michelangelo", 1367, "statue");
        sendStartAuctionMessage(e);
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        addBehaviour(new GeneralBehaviour(this, msg));
    }
    
    /**
     * method that searches for all the museums and save them
     */
    private void findMuseums(){
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("offer catalog");
        dfd.addServices(sd);
        DFAgentDescription[] result = null;
        try {
            result = DFService.search(this, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(TourGuideAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("<" + getLocalName() + ">: found " + result.length + " museums");
        for(DFAgentDescription description : result){
            museums.add(description.getName());
        }
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
