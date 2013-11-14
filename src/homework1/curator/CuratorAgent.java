package homework1.curator;

import homework1.curator.behaviours.IncomingMessageHandler;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

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

        Object[] args = getArguments();
        int situation = 0;
        if (args != null) {
            situation = Integer.parseInt((String) args[0]);
        }else{
            System.out.println("<" + getLocalName() + ">: particular museum not set -> terminating");
            return;
        }
        
        ElementsDatabase.createDatabase(situation);
        
        System.out.println("<" + getLocalName() + ">: database generated");
        
        MessageTemplate mt = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
        addBehaviour(new IncomingMessageHandler(this, mt));

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
