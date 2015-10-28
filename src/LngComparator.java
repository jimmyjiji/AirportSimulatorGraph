import java.util.Comparator;

/**
 * Created by Jimmy on 5/4/2015 in PACKAGE_NAME
 */
public class LngComparator implements Comparator<City> {

    @Override
    public int compare(City o1, City o2) {
        if (o1.getLocation().getLng() > o2.getLocation().getLng())
            return 1;
        else if (o1.getLocation().getLng() < o2.getLocation().getLng())
            return -1;
        else
            return 0;
    }
}
