package homework1.profiler.behaviours;

import homework1.profiler.ProfilerAgent;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.states.MsgReceiver;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Behaviour that is supposed to recieve the message from the tour guide agent,
 * its ussualy chained with the asking behaviour
 *
 * @author zikesjan
 */
public class TourInformationsReceiver extends MsgReceiver {

    private ProfilerAgent tga;

    public TourInformationsReceiver(ProfilerAgent a, MessageTemplate mt, long deadline, DataStore s, Object msgKey) {
        super(a, mt, deadline, s, msgKey);
        this.tga = a;
    }

    @Override
    protected void handleMessage(ACLMessage msg) {
        if (msg == null) {
            System.out.println("<" + myAgent.getLocalName() + ">: incorrect message");
        } else {
            System.out.println("<" + myAgent.getLocalName() + ">: message with the museums and its details recieved");

            String[][] data = null;
            try {
                data = (String[][]) msg.getContentObject();
            } catch (UnreadableException ex) {
                Logger.getLogger(TourInformationsReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < 3; i++) {
                String[] items = new String[3];
                for (int j = 0; j < 4; j++) {
                    if (j == 0) {
                        if (data[i][0] != null || !"".equals(data[i][0])) {
                            tga.museums.add(data[i][0]);

                        }
                    } else {
                        items[j - 1] = data[i][j];
                    }
                    tga.map.put(data[i][0], items);
                }
            }
            tga.askForTheMuseum();
        }
    }
}
