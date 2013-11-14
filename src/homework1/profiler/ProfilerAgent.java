package homework1.profiler;


import homework1.profiler.behaviours.AskForTourRequestBehaviour;
import homework1.profiler.behaviours.GetDetailsBehaviour;
import homework1.profiler.behaviours.TourInformationsReceiver;
import homework1.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Profoler Agent that represents users profile
 *
 * @author zikesjan
 */
public class ProfilerAgent extends Agent {

    AID id = new AID("profiler", AID.ISLOCALNAME);
    public Profile p;
    AID tourGuide;

    @Override
    protected void setup() {

        System.out.println("<" + getLocalName() + ">: started");

        ArrayList<String> interests = new ArrayList<String>();
        interests.add("cars");
        interests.add("planes");
        interests.add("computers");
        interests.add("paintings");
        p = new Profile("user1", 25, "student at KTH", interests);

        System.out.println("<" + getLocalName() + ">: profile created");

        //getDetails("curator", "Venus");
        askForTour();
        
        
        //searching offered tours in services
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("offer tour");
        dfd.addServices(sd);
        DFAgentDescription[] result = null;
        try {
            result = DFService.search(this, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(TourGuideAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("<" + getLocalName() + ">: found " + result.length + " tour guides");
        if (result.length > 0) {
            tourGuide = result[0].getName();
            System.out.println("<" + getLocalName() + ">: found " + result[0].getName());
        }

        //TODO ask the tourguide for tour
        //TODO ask user to chose museum
        //TODO ask the curator for details

    }

    private void getDetails(String museum, String artefact) {
        //searching available offer artifact
        
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        request.addReceiver(new AID(museum, AID.ISLOCALNAME));
        addBehaviour(new GetDetailsBehaviour(this, request, artefact));
        /*DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("offer artifact details");
        dfd.addServices(sd);
        DFAgentDescription[] result = null;
        try {
            result = DFService.search(this, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(TourGuideAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("<" + getLocalName() + ">: found " + result.length + " museums");
        if (result.length > 0) {
            for (DFAgentDescription desc : result) {
                System.out.println("<" + getLocalName() + ">: found " + desc.getName());
            }
        }*/
    }
    
    private void askForTour(){
        /*ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        request.addReceiver(new AID("tour_guide", AID.ISLOCALNAME));
        addBehaviour(new AskForTourBehaviour(this, request, p));*/
        SequentialBehaviour sb = new SequentialBehaviour(this);
        MessageTemplate mt = MessageTemplate.MatchSender(new AID("tour_guide", AID.ISLOCALNAME));
        DataStore ds = new DataStore();
        sb.addSubBehaviour(new AskForTourRequestBehaviour(this));
        sb.addSubBehaviour(new TourInformationsReceiver(this, mt, MsgReceiver.INFINITE, ds, "key"));
        //TODO add paralel getting catalogs from museums
        
        addBehaviour(sb);
    }
    
    public void askForTheMuseum(){
        
    }
}
