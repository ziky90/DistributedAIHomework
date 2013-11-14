package homework1.profiler.behaviours;

import homework1.profiler.ProfilerAgent;
import homework1.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

/**
 *
 * @author zikesjan
 */
public class TourInformationsReceiver extends MsgReceiver{
    
    
    private ProfilerAgent tga;
    
    public TourInformationsReceiver(ProfilerAgent a, MessageTemplate mt, long deadline, DataStore s, Object msgKey) {
        super(a, mt, deadline, s, msgKey);
        this.tga = a;
    }

    
    @Override
    protected void handleMessage(ACLMessage msg){
        if(msg == null){
            //error message
            System.out.println("<" + myAgent.getLocalName() + ">: incorrect message");
        }else{
            
            System.out.println("<" + myAgent.getLocalName() + ">: message with the museums and its details");
            //save profile
        }
    }
}