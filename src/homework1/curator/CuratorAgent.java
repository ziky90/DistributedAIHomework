package homework1.curator;

import homework1.curator.behaviours.OfferArtifactsDetails;
import homework1.curator.behaviours.OfferCatalog;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Agent that represents the curator
 *
 * @author zikesjan
 */
public class CuratorAgent extends Agent {

    AID id = new AID("curator", AID.ISLOCALNAME);

    @Override
    protected void setup() {
        System.out.println("<" + getLocalName() + ">: started");

        ParallelBehaviour mainBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
        mainBehaviour.addSubBehaviour(new OfferArtifactsDetails());
        mainBehaviour.addSubBehaviour(new OfferCatalog());

        System.out.println("<" + getLocalName() + ">: behaviour set up");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(id);
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
