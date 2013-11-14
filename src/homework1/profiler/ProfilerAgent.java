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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Profoler Agent that represents users profile
 *
 * @author zikesjan
 */
public class ProfilerAgent extends Agent {

    public AID id = new AID("profiler", AID.ISLOCALNAME);
    public Profile p;
    public AID tourGuide;
    public ArrayList<String> museums = new ArrayList<String>();
    public HashMap<String, String[]> map = new HashMap<String, String[]>();

    @Override
    protected void setup() {

        System.out.println("<" + getLocalName() + ">: started");

        ArrayList<String> interests = new ArrayList<String>();
        interests.add("car");
        interests.add("plane");
        interests.add("computer");
        interests.add("painting");
        p = new Profile("user1", 25, "student at KTH", interests);

        System.out.println("<" + getLocalName() + ">: profile created");

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

    }

    private void getDetails(String museum, String artefact) {
        //searching available offer artifact

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        request.addReceiver(new AID(museum, AID.ISLOCALNAME));
        addBehaviour(new GetDetailsBehaviour(this, request, artefact));

    }

    private void askForTour() {
        SequentialBehaviour sb = new SequentialBehaviour(this);
        MessageTemplate mt = MessageTemplate.MatchSender(new AID("tour_guide", AID.ISLOCALNAME));
        DataStore ds = new DataStore();
        sb.addSubBehaviour(new AskForTourRequestBehaviour(this));
        sb.addSubBehaviour(new TourInformationsReceiver(this, mt, MsgReceiver.INFINITE, ds, "key"));
        addBehaviour(sb);
    }

    public void askForTheMuseum() {
        int i = 0;
        for (String s : museums) {
            if (s != null) {
                System.out.println(i + ") " + s);
                i++;
            }
        }
        System.out.println("Type the museums number: ");

        int museum = 0;

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();
            museum = Integer.parseInt(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (museum > i - 1) {
            askForTheMuseum();
        } else {
            for (String s : map.get(museums.get(museum))) {
                getDetails(museums.get(museum), s);
            }
        }
    }
}
