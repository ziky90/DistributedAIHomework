package homework1.tourguide.behaviours;

import jade.lang.acl.ACLMessage;
import jade.proto.states.MsgReceiver;

/**
 * Behavior that waits for all the registrations from profiler agents
 *
 * @author zikesjan
 */
public class RegistrationsReciever extends MsgReceiver {

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            String name = msg.getContent();
            String[] arguments = {name};
            myAgent.setArguments(arguments);
        }

    }
}
