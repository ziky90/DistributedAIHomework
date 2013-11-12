package homework1.tourguide.behaviours;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

/**
 * Behaviour that requests for the catalog from the curator
 * @author zikesjan
 */
public class CatalogRequest extends SimpleAchieveREInitiator{
    
    public CatalogRequest(Agent a,  ACLMessage am){
        super(a, am);
    }
}
