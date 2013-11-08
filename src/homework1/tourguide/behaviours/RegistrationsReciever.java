package homework1.tourguide.behaviours;

import homework1.profiler.Profile;
import homework1.tourguide.TourGuideAgent;
import jade.lang.acl.ACLMessage;
import jade.proto.states.MsgReceiver;

/**
 * Behavior that waits for all the registrations from profiler agents
 *
 * @author zikesjan
 */
public class RegistrationsReciever extends MsgReceiver {
    
    private TourGuideAgent tga;
    
    public RegistrationsReciever(TourGuideAgent a) {
        this.tga = a;
    }

    
    
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            String name = msg.getContent();
            tga.addProfile(new Profile(name));
            //TODO add the field to the tga and sae there name
        }

    }
}
