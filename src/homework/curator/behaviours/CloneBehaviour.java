package homework.curator.behaviours;

import homework.curator.CuratorAgent;
import jade.core.Location;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author zikesjan
 */
public class CloneBehaviour extends OneShotBehaviour {

    private Location l;
    private int order;

    public CloneBehaviour(Location l, int order) {
        super();
        this.l = l;
        this.order = order;
    }

    @Override
    public void action() {
        if (!l.getName().equals("Main-Container") && myAgent.getName().charAt(1) != 'l') {
            String newName = "Clone"+ order + myAgent.getLocalName() + l.getName();
            myAgent.doClone(l, newName);
            System.out.println("<" + myAgent.getLocalName() + ">: cloned to: "+l.getName()+", clone no. "+order);
        }
    }
}
