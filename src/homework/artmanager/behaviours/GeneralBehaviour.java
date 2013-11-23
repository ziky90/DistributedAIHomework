package homework.artmanager.behaviours;

import homework.artmanager.ArtManagerAgent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import java.util.Vector;

/**
 * Basicaly general Dutch Auction behaviour
 * @author zikesjan
 */
public class GeneralBehaviour extends ContractNetInitiator {

    private ArtManagerAgent ama;

    public GeneralBehaviour(ArtManagerAgent a, ACLMessage cfp) {
        super(a, cfp);
        this.ama = a;
    }

    /**
     * First of all I am very sorry for using Vectors instead of Collections,
     * but ancient jade framework forced me to do so
     * Method that reads all the incoming messages from the bidders
     *
     * @param responses
     * @param acceptances
     */
    @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        Vector<ACLMessage> messages = new Vector<ACLMessage>();
        for (ACLMessage msg : (Vector<ACLMessage>) responses) {
            messages.add(msg.createReply());

            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                System.out.println("<" + myAgent.getLocalName() + ">: recieved bid from" + msg.getSender().getLocalName() + ", " + msg.getContent());

                ama.winner = msg.getSender();
                ama.finnish = true;

            } else if (msg.getPerformative() == ACLMessage.REFUSE) {
                System.out.println("<" + myAgent.getLocalName() + ">: rejected bid from" + msg.getSender().getLocalName());
            }
        }

        if (ama.winner != null) {
            System.out.println("<" + myAgent.getLocalName() + ">: auction winner" + ama.winner.getLocalName());
            for (ACLMessage msg : messages) {
                msg.setPerformative(ACLMessage.INFORM);

                msg.setContent(ama.winner.getLocalName());

                acceptances.add(msg);
            }

        } else {
            System.out.println("<" + myAgent.getLocalName() + ">: auctioner sending new cfp");
        }
    }

    /**
     * First of all I am very sorry for using Vectors instead of Collections,
     * but ancient jade framework forced me to do so
     * Method creates actual cfp with the actual price for which can buyers buy the item
     *
     * @param msg
     * @return
     */
    @Override
    protected Vector prepareCfps(ACLMessage msg) {
        msg.setContent(""+ama.currentPrice);
        Vector messages = new Vector();
        msg = ama.addReceivers(msg);
        messages.add(msg);
        System.out.println("<" + myAgent.getLocalName() + ">: CFP for: "+ ama.currentPrice);
        return messages;
    }

    /**
     * Whenever one round of the auction ends the price is eithe decreased or auction finished
     * @return 
     */
    @Override
    public int onEnd() {
        if (ama.currentPrice == 0) {
            // stop
        } else if (!ama.finnish) {
            ama.currentPrice--;
            myAgent.addBehaviour(new GeneralBehaviour(ama, new ACLMessage(ACLMessage.CFP)));
        }
        return super.onEnd();
    }
}
