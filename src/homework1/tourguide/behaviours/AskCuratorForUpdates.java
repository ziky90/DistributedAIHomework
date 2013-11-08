package homework1.tourguide.behaviours;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

/**
 * behaviour that periodicaly asks the curator for updates about the galeries exibitions, etc.
 * 
 * @author zikesjan
 */
public class AskCuratorForUpdates extends TickerBehaviour{
    
    public AskCuratorForUpdates(Agent a, long period) {
        super(a, period);
    }
    
    @Override
    protected void onTick() {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
