package homework1.tourguide;

import homework1.tourguide.behaviours.RegistrationsReciever;
import jade.core.AID;
import jade.core.Agent;

/**
 * Tour guide agent that represents thetour guide
 * @author zikesjan
 */
public class TourGuideAgent extends Agent{
    
    AID id = new AID("tour_guide",AID.ISLOCALNAME);
    
    @Override
    protected void setup(){
        System.out.println("Hello this is tour guide agent: "+getAID().getName());
        
        addBehaviour(new RegistrationsReciever());
    }
    
}
