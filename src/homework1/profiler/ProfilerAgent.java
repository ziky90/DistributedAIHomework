package homework1.profiler;

import homework1.profiler.behaviours.RegisterAtTourGuide;
import jade.core.AID;
import jade.core.Agent;
import java.util.ArrayList;

/**
 * Profoler Agent that represents users profile
 * @author zikesjan
 */
public class ProfilerAgent extends Agent{
    
    AID id = new AID("profiler",AID.ISLOCALNAME);
    
    @Override
    protected void setup(){
        Profile p = new Profile();
        p.setName(id.getName());
        p.setAge(23);
        p.setOcupancy("job");
        addBehaviour(new RegisterAtTourGuide(p));
    }
    
    
    
}
