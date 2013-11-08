package homework1.tourguide;

import jade.core.AID;
import jade.core.Agent;

/**
 *
 * @author zikesjan
 */
public class TourGuideAgent extends Agent{
    
    AID id = new AID("tour_guide",AID.ISLOCALNAME);
    
    @Override
    protected void setup(){
        System.out.println("Hello this is tour guide agent: "+getAID().getName());
    }
    
}
