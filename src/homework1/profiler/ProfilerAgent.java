package homework1.profiler;



import jade.core.AID;
import jade.core.Agent;

/**
 *
 * @author zikesjan
 */
public class ProfilerAgent extends Agent{
    
    AID id = new AID("profiler",AID.ISLOCALNAME);
    
    @Override
    protected void setup(){
        System.out.println("Hello this is profiler agent: "+getAID().getName());
    }
    
}
