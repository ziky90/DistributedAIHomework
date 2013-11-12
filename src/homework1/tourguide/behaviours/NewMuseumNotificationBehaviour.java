package homework1.tourguide.behaviours;

import homework1.tourguide.TourGuideAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behaviour that get notified when the new museum is available
 * @author zikesjan
 */
public class NewMuseumNotificationBehaviour extends CyclicBehaviour {

    private TourGuideAgent tga;

    public NewMuseumNotificationBehaviour(TourGuideAgent a) {
        super(a);
        this.tga = a;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchSender(myAgent.getDefaultDF()));

        if (msg != null) {
            try {
                DFAgentDescription[] dfds = DFService.decodeNotification(msg.getContent());
                if (dfds.length > 0) {
                    tga.museums.add(dfds[0].getName());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        block();
    }
}
