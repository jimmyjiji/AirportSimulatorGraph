import java.util.Comparator;

/**
 * Created by Jimmy on 5/4/2015 in PACKAGE_NAME
 */
public class NameComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
