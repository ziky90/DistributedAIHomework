package homework.curator.behaviours;

import homework.curator.CuratorAgent;
import homework.curator.Element;
import jade.core.Location;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREResponder;

/**
 * Behaviour that deals with bidding and other interactions with the
 * DutchAuction
 *
 * @author zikesjan
 */
public class DutchAuctionResponderBehaviour extends SimpleAchieveREResponder {

    private CuratorAgent ca;
    private Element elementForBuy;

    public DutchAuctionResponderBehaviour(CuratorAgent a, MessageTemplate msg) {
        super(a, msg);
        this.ca = a;
    }

    /**
     * Method that handles requests and send appropriate responses
     *
     * @param request
     * @return
     */
    @Override
    protected ACLMessage prepareResponse(ACLMessage request) {
        if (request != null) {
            ACLMessage reply = request.createReply();
            int requestType = request.getPerformative();
            if (requestType == ACLMessage.INFORM) {
                System.out.println("<" + myAgent.getLocalName() + ">: recieved INFORM from dutch auction");
                try {
                    elementForBuy = (Element) request.getContentObject();
                } catch (UnreadableException ex) {
                    String winner = request.getContent();
                    if (winner.equals(ca.getLocalName())) {
                        ca.ed.addElement(elementForBuy);
                        for(Location l : ca.locations){
                            if(l.getName().equals("Main-Container")){
                                myAgent.doMove(l);
                            }
                        }
                        System.out.println("<" + myAgent.getLocalName() + ">: winner of the auction " + elementForBuy.getName() + " saved to the database");
                    } else {
                        for(Location l : ca.locations){
                            if(l.getName().equals("Main-Container")){
                                myAgent.doMove(l);
                            }
                        }
                        System.out.println("<" + myAgent.getLocalName() + ">: action ended we've lost");
                    }
                }
                return null;
            } else if (requestType == ACLMessage.CFP) {
                System.out.println("<" + myAgent.getLocalName() + ">: recieved CFP from dutch auction");
                int currentPrice = Integer.parseInt(request.getContent());
                if (currentPrice > ca.maxBid) {
                    reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    reply.setPerformative(ACLMessage.REFUSE);
                    System.out.println("<" + myAgent.getLocalName() + ">: : REJECT : " + currentPrice);
                    return reply;
                } else {
                    int difference = currentPrice - ca.optimalBid;
                    if (difference == 0 || (int) (Math.random() * difference) == difference - 1) {
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setPerformative(ACLMessage.PROPOSE);
                        System.out.println("<" + myAgent.getLocalName() + ">: : ACCEPT : " + currentPrice);
                        return reply;
                    } else {
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        reply.setPerformative(ACLMessage.REFUSE);
                        System.out.println("<" + myAgent.getLocalName() + ">: : REJECT : " + currentPrice);
                        return reply;
                    }
                }

            } else if (requestType == ACLMessage.REQUEST) {
                //System.out.println("<" + myAgent.getLocalName() + ">: recieved request from dutch auction");
            }
        }
        return null;
    }
}
