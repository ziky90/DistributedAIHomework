package homework1.profiler.behaviours;

import homework1.profiler.Profile;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * behaviour that will register the particular agent at the tour guide agent
 * 
 * @author zikesjan
 */
public class RegisterAtTourGuide extends OneShotBehaviour{

    private Profile p;
    
    public RegisterAtTourGuide(Profile p){
        this.p = p;
    }
    
    @Override
    public void action() {
        ACLMessage inform = new ACLMessage(ACLMessage.INFORM); 
        inform.addReceiver(new AID("tour_guide", AID.ISLOCALNAME));
        inform.setContent(p.getName());
        myAgent.send(inform);
    }
    
}
