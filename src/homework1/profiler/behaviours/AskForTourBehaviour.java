package homework1.profiler.behaviours;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

/**
 * Behaviour for asking the tour guide about the suggested tour
 * @author zikesjan
 */
public class AskForTourBehaviour extends SimpleAchieveREInitiator{
    public AskForTourBehaviour(Agent a, ACLMessage am){
            super(a, am);
        }
}
