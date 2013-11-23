package homework.curator.behaviours;

import homework.curator.CuratorAgent;
import homework.curator.Element;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREResponder;

/**
 * Behaviour that deals with bidding and other interactions with the DutchAuction
 * @author zikesjan
 */
public class DutchAuctionResponderBehaviour extends SimpleAchieveREResponder{
    
    private CuratorAgent ca;
    private Element elementForBuy;
    
    public DutchAuctionResponderBehaviour(CuratorAgent a, MessageTemplate msg){
        super(a, msg);
        this.ca = a;
    }
    
    /**
     * Method that handles requests and send appropriate responses
     * @param request
     * @return 
     */
    @Override
    protected ACLMessage prepareResponse(ACLMessage request){
        if(request != null){
            ACLMessage reply = request.createReply();
            int requestType = request.getPerformative();
            if(requestType == ACLMessage.INFORM){
                System.out.println("<" + myAgent.getLocalName() + ">: recieved INFORM from dutch auction");
                try {
                    elementForBuy = (Element) request.getContentObject();
                } catch (UnreadableException ex) {
                    String winner = request.getContent();
                    if(winner.equals(ca.getLocalName())){
                        ca.ed.addElement(elementForBuy);
                        System.out.println("<" + myAgent.getLocalName() + ">: winner of the auction "+elementForBuy.getName()+" saved to the database");
                    }else{
                        System.out.println("<" + myAgent.getLocalName() + ">: action ended we've lost");
                    }
                }
                return null;
            }else if(requestType == ACLMessage.CFP){
                System.out.println("<" + myAgent.getLocalName() + ">: recieved CFP from dutch auction");
                int currentPrice = Integer.parseInt(request.getContent());
                //TODO AI deciding about price to bid
                
            }else if(requestType == ACLMessage.REQUEST){
                //System.out.println("<" + myAgent.getLocalName() + ">: recieved request from dutch auction");
            }
        }
        return null;
    }
}
