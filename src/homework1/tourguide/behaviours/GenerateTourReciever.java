package homework1.tourguide.behaviours;

import homework1.profiler.Profile;
import homework1.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behaviour that chose the museum based on the profile
 *
 * @author zikesjan
 */
public class GenerateTourReciever extends CyclicBehaviour {

    private TourGuideAgent tga;
    private MessageTemplate mt;

    public GenerateTourReciever(TourGuideAgent a, MessageTemplate mt) {
        this.tga = a;
        this.mt = mt;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            int pos = 0;
            for (AID aid : tga.museums) {
                tga.informations[pos][0] = aid.getLocalName();
                pos++;
            }
            try {
                tga.p = (Profile) msg.getContentObject();
            } catch (UnreadableException ex) {
                Logger.getLogger(GenerateTourReciever.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("<" + myAgent.getLocalName() + ">: message recieved and data stored from "+tga.p.getName());
            
            
            
            SequentialBehaviour sb = new SequentialBehaviour(myAgent);
            ParallelBehaviour pb = new ParallelBehaviour(myAgent, ParallelBehaviour.WHEN_ALL);
            for (AID aid : tga.museums) {
                ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                request.addReceiver(aid);
                pb.addSubBehaviour(new CatalogRequest(tga, request));
            }
            sb.addSubBehaviour(pb);
            sb.addSubBehaviour(new RecommedMuseum(tga));
            sb.addSubBehaviour(new TourSenderBehaviour(tga));
            myAgent.addBehaviour(sb);
        }
    }
    
    
}
