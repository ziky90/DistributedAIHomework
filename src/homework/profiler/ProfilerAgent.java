package homework.profiler;

import homework.profiler.behaviours.AskForTourRequestBehaviour;
import homework.profiler.behaviours.GetDetailsBehaviour;
import homework.profiler.behaviours.TourInformationsReceiver;
import homework.tourguide.TourGuideAgent;
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

    /**
     * setup is used for generation of the profile which can be changed for
     * reading from gui or better android app and then asking for tour
     */
    @Override
    protected void setup() {
        System.out.println("<" + getLocalName() + ">: started");

        //creating the profile
        ArrayList<String> interests = new ArrayList<String>();
        interests.add("car");
        interests.add("plane");
        interests.add("computer");
        interests.add("painting");
        p = new Profile("user1", 25, "student at KTH", interests);
        System.out.println("<" + getLocalName() + ">: profile created");

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

        //running the method that asks for generating the tours
        askForTour();
    }

    /**
     * methods that asks for the details about the given artifact
     *
     * @param museum name of the museum
     * @param artefact name of the artifact
     */
    private void getDetails(String museum, String artefact) {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        request.addReceiver(new AID(museum, AID.ISLOCALNAME));
        addBehaviour(new GetDetailsBehaviour(this, request, artefact));
    }

    /**
     * method that asks for the tour to the tourguide agent
     */
    private void askForTour() {
        SequentialBehaviour sb = new SequentialBehaviour(this);
        MessageTemplate mt = MessageTemplate.MatchSender(new AID("tour_guide", AID.ISLOCALNAME));
        DataStore ds = new DataStore();
        sb.addSubBehaviour(new AskForTourRequestBehaviour(this));
        sb.addSubBehaviour(new TourInformationsReceiver(this, mt, MsgReceiver.INFINITE, ds, "key"));
        addBehaviour(sb);
    }

    /**
     * method that asks for all the details from one particular museum
     */
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
