package homework.curator;

import homework.curator.behaviours.DutchAuctionResponderBehaviour;
import homework.curator.behaviours.IncomingMessageHandler;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.CloneAction;
import jade.domain.mobility.MobileAgentDescription;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public ArrayList<Location> locations = new ArrayList<Location>();
    private ArrayList<String> agents = new ArrayList<String>();

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

        //----------------------------------------------------------------------
        //assignment 3
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(MobilityOntology.getInstance());
        sendRequest(new Action(getAMS(), new QueryPlatformLocationsAction()));
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchSender(getAMS()),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        ACLMessage resp = blockingReceive(mt);
        ContentElement ce = null;
        try {
            ce = getContentManager().extractContent(resp);
        } catch (CodecException ex) {
            Logger.getLogger(CuratorAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UngroundedException ex) {
            Logger.getLogger(CuratorAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(CuratorAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        Result result = (Result) ce;
        jade.util.leap.Iterator it = result.getItems().iterator();
        while (it.hasNext()) {
            locations.add((Location) it.next());
        }
        cloneAgent();
    }

    /**
     * metod that clones the agent
     */
    private void cloneAgent() {
        System.out.println("<" + getLocalName() + ">: cloning proces started");
        String agentName = getLocalName();
        AID aid = new AID(agentName, AID.ISLOCALNAME);

        for (Location dest : locations) {
            MobileAgentDescription mad = new MobileAgentDescription();
            mad.setName(aid);
            mad.setDestination(dest);
            String newName = "Clone-" + agentName;
            doClone(dest, newName);
        }
        System.out.println("<" + getLocalName() + ">: cloned");
    }

    void sendRequest(Action action) {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setLanguage(new SLCodec().getName());
        request.setOntology(MobilityOntology.getInstance().getName());
        try {
            getContentManager().fillContent(request, action);
            request.addReceiver(action.getActor());
            send(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
