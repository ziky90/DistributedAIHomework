package homework.tourguide.behaviours;

import homework.tourguide.TourGuideAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Inner AI recommendation system. It is prepared for advanced AI/ML techniques,
 * but currently it only offer all the museums with all the artifacts
 *
 * @author zikesjan
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
            tga.informations[pos][0] = museum.getLocalName();
            int counter = 1;
            for (String s2 : tga.catalog.get(museum)) {
                tga.informations[pos][counter] = s2;
                counter++;
            }
        }
        pos++;
    }
}
