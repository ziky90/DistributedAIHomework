package homework1.tourguide.behaviours;

import homework1.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behaviour thet sends the details about the museums to the profiler
 * @author zikesjan
 */
public class TourSenderBehaviour extends OneShotBehaviour{

    private TourGuideAgent tga;
    
    public TourSenderBehaviour(TourGuideAgent a){
        super(a);
        this.tga = a;
    }
    
    @Override
    public void action() {
        System.out.println("<" + myAgent.getLocalName() + ">: sending the informations about the tour");
        ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
        inform.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        inform.addReceiver(new AID("profiler", AID.ISLOCALNAME));
        try {
            inform.setContentObject(tga.informations);
        } catch (IOException ex) {
            Logger.getLogger(TourSenderBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        myAgent.send(inform);
    }
    
}
