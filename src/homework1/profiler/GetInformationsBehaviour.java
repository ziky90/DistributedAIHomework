package homework1.profiler;

import jade.core.Agent;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

/**
 * Information behavior for the profiler agent to ask for the detailed
 * informations to the curator agent
 *
 * @author zikesjan
 */
public class GetInformationsBehaviour extends SimpleAchieveREResponder {

    public GetInformationsBehaviour(Agent a, MessageTemplate msg) {
        super(a, msg);
    }
}
