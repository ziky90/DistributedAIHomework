package homework1.tourguide;

import homework1.profiler.Profile;
import homework1.tourguide.behaviours.RegistrationsReciever;
import jade.core.AID;
import jade.core.Agent;
import java.util.HashSet;
import java.util.Set;

/**
 * Tour guide agent that represents thetour guide
 * @author zikesjan
 */
public class TourGuideAgent extends Agent{
    
    AID id = new AID("tour_guide",AID.ISLOCALNAME);
    private Set<Profile> profiles;
    
    @Override
    protected void setup(){
        System.out.println("Hello this is tour guide agent: "+getAID().getName());
        
        addBehaviour(new RegistrationsReciever(this));
    }
    
    public void addProfile(Profile p){
        if(profiles == null){
             profiles = new HashSet<Profile>();
        }
        profiles.add(p);
    }
    
    
}
