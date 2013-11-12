package homework1.profiler.behaviours;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

/**
 * Information behavior for the profiler agent to ask for the detailed
 * informations to the curator agent
 *
 * @author zikesjan
 */
public class GetDetailsBehaviour extends SimpleAchieveREInitiator {

        public GetDetailsBehaviour(Agent a, ACLMessage am){
            super(a, am);
        }
    
}
