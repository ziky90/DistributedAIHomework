package homework1.tourguide.behaviours;

import homework1.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

/**
 * Behaviour that chose the museum based on the profile
 *
 * @author zikesjan
 */
public class GenerateTourReciever extends MsgReceiver {

    private TourGuideAgent tga;
    
    public GenerateTourReciever(TourGuideAgent a, MessageTemplate mt, long deadline, DataStore s, Object msgKey) {
        super(a, mt, deadline, s, msgKey);
        this.tga = a;
    }

    
    @Override
    protected void handleMessage(ACLMessage msg){
        if(msg == null){
            //error message
            System.out.println("<" + myAgent.getLocalName() + ">: incorrect message");
        }else{
            int pos = 0;
            for(AID aid : tga.museums){
                tga.informations[pos][0] = aid.getLocalName();
                pos++;
            }
            System.out.println("<" + myAgent.getLocalName() + ">: message recieved and data stored");
            //save profile
        }
    }
    
}
