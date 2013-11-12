package homework1.tourguide;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tour guide agent that represents thetour guide
 *
 * @author zikesjan
 */
public class TourGuideAgent extends Agent {

    private AID id = new AID("tour_guide", AID.ISLOCALNAME);
    public ArrayList<AID> museums = new ArrayList<AID>();

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
        if (result.length > 0) {
            for (DFAgentDescription desc : result) {
                museums.add(desc.getName());
                System.out.println("<" + getLocalName() + ">: found " + desc.getName());
            }
        }

        //subscription for new museums
        dfd = new DFAgentDescription();
        sd = new ServiceDescription();
        sd.setType("offer catalog");
        dfd.addServices(sd);
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1));
        send(DFService.createSubscriptionMessage(this, getDefaultDF(),dfd, sc));

        //TODO create service behaviurs
        
        //registering service
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

    }
}
