package homework1.tourguide.behaviours;

import jade.core.Agent;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

/**
 * Behaviour that chose the museum based on the profile
 *
 * @author zikesjan
 */
public class GenerateTour extends SimpleAchieveREResponder {

    public GenerateTour(Agent a, MessageTemplate mt) {
        super(a, mt);
    }
}
