package homework.curator;

import homework.curator.behaviours.DutchAuctionResponderBehaviour;
import homework.curator.behaviours.IncomingMessageHandler;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Agent that represents the curator. One instance stands for one particular
 * museum.
 *
 * @author zikesjan
 */
public class CuratorAgent extends Agent {

    public int maxBid;
    public int optimalBid;
    public ElementsDatabase ed = new ElementsDatabase();

    /**
     * Agent is just starting the behaviour that handles all the requests and
     * register them as services to the DF catalog
     */
    @Override
    protected void setup() {
        System.out.println("<" + getLocalName() + ">: started");

        Object[] args = getArguments();
        int situation;
        if (args != null) {
            situation = Integer.parseInt((String) args[0]);
        } else {
            System.out.println("<" + getLocalName() + ">: particular museum not set -> terminating");
            return;
        }

        System.out.println("<" + getLocalName() + ">: situation " + situation);
        ed.createDatabase(situation);
        switch (situation) {
            case (0): {
                maxBid = 100;
                optimalBid = 80;
                break;
            }
            case (1): {
                maxBid = 126;
                optimalBid = 60;
                break;
            }
            case (2): {
                maxBid = 116;
                optimalBid = 85;
                break;
            }
        }

        System.out.println("<" + getLocalName() + ">: database generated");

        //assignment1 relict
        //MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
        //addBehaviour(new IncomingMessageHandler(this, mt));

        //assignment2
        MessageTemplate msgTemp = MessageTemplate.or(
                MessageTemplate.or(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchPerformative(ACLMessage.CFP)),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        addBehaviour(new DutchAuctionResponderBehaviour(this, msgTemp));

        System.out.println("<" + getLocalName() + ">: behaviour set up");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd1 = new ServiceDescription();
        ServiceDescription sd2 = new ServiceDescription();
        sd1.setType("offer artifact details");
        sd1.setName(getLocalName());
        sd2.setType("offer catalog");
        sd2.setName(getLocalName());
        dfd.addServices(sd1);
        dfd.addServices(sd2);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        System.out.println("<" + getLocalName() + ">: registered to the DF");
    }
}
