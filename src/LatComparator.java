import java.util.Comparator;

/**
 * Created by Jimmy on 5/4/2015 in PACKAGE_NAME
 */
public class LatComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1.getLocation().getLat() > o2.getLocation().getLat())
            return 1;
        else if (o1.getLocation().getLat() < o2.getLocation().getLat())
            return -1;
        else
            return 0;
    }
}
