package homework1.profiler;

import homework1.profiler.behaviours.RegisterAtTourGuide;
import jade.core.AID;
import jade.core.Agent;
import java.util.ArrayList;

/**
 * Profoler Agent that represents users profile
 *
 * @author zikesjan
 */
public class ProfilerAgent extends Agent {

    AID id = new AID("profiler", AID.ISLOCALNAME);

    @Override
    protected void setup() {
        //creating profile object from the informations passed as the arguments 
        Object[] args = getArguments();
        Profile p = null;
        if (args != null) {
            p = new Profile();
            p.setName((String) args[0]);
            p.setAge(Integer.parseInt((String) args[1]));
            p.setOcupancy((String) args[2]);
            ArrayList<String> interests = new ArrayList<String>();
            for (int i = 3; i < args.length; i++) {
                interests.add((String) args[i]);
            }
            p.setInterests(interests);
        }

        if(p != null){
            p.setName(id.getName());
            p.setAge(23);
            p.setOcupancy("job");
            addBehaviour(new RegisterAtTourGuide(p));
        }else{
            System.out.println("Please set your personal informations");
        }
    }
}
