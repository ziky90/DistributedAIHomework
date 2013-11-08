package homework1.curator;

import jade.core.AID;
import jade.core.Agent;

/**
 *
 * @author zikesjan
 */
public class CuratorAgent extends Agent{
    
    AID id = new AID("curator",AID.ISLOCALNAME);
    
    @Override
    protected void setup(){
        System.out.println("Hello this is curator agent: "+getAID().getName());
    }
    
}
