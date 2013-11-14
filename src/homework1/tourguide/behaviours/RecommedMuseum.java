package homework1.tourguide.behaviours;

import homework1.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;

/**
 * AI and ML based recommendation system
 *
 */
public class RecommedMuseum extends OneShotBehaviour {

    private TourGuideAgent tga;

    public RecommedMuseum(TourGuideAgent tga) {
        super(tga);
        this.tga = tga;
    }

    @Override
    public void action() {

        int pos = 0;
        for (AID museum : tga.museums) {
            //System.out.println(museum);
            tga.informations[pos][0] = museum.getLocalName();
            int counter = 1;
            for (String s2 : tga.catalog.get(museum)) {
                System.out.println("<" + myAgent.getLocalName() + ">: match found");
                tga.informations[pos][counter] = s2;
                counter++;
            }

        }
        pos++;
    }
}
