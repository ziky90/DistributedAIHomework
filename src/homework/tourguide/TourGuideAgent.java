package homework.tourguide;

import homework.profiler.Profile;
import homework.tourguide.behaviours.GenerateTourReciever;
import homework.tourguide.behaviours.NewMuseumNotificationBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tour guide agent that represents the tour guide that recomends the museum
 * based on the users preferences
 *
 * @author zikesjan
 */
public class TourGuideAgent extends Agent {

    private AID id = new AID("tour_guide", AID.ISLOCALNAME);
    public ArrayList<AID> museums = new ArrayList<AID>();
    public String[][] informations = new String[3][4];
    public Profile p;
    public HashMap<AID, String[]> catalog = new HashMap<AID, String[]>();

    @Override
    protected void setup() {
        System.out.println("<" + getLocalName() + ">: started");

        //searching offer catalog in services
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

        //subscription for new museums and adding the behaviour that listens for it
        dfd = new DFAgentDescription();
        sd = new ServiceDescription();
        sd.setType("offer catalog");
        dfd.addServices(sd);
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1));
        send(DFService.createSubscriptionMessage(this, getDefaultDF(), dfd, sc));
        addBehaviour(new NewMuseumNotificationBehaviour(this));

        //registering service offer tour
        dfd = new DFAgentDescription();
        dfd.setName(id);
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setType("offer tour");
        sd1.setName(getLocalName());
        dfd.addServices(sd1);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
        addBehaviour(new GenerateTourReciever(this, mt));
    }
}
