package model;

import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.model.Maniple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GroupTest {

    private final Cohort cohort14 = new Cohort("14");
    private final Maniple manipleI = new Maniple("I");
    private final Maniple manipleW = new Maniple("W");
    private final Century centuryI14a = new Century("a", 35);
    private final Century centuryI14b = new Century("b", 30);
    private final Century centuryW14a = new Century("a", 40);
    private final Century centuryW14b = new Century("b", 35);

    @Before
    public void setUp() {
        cohort14.setManiples(Arrays.asList(manipleI, manipleW));
        manipleI.setCenturies(Arrays.asList(centuryI14a, centuryI14b));
        manipleW.setCenturies(Arrays.asList(centuryW14a, centuryW14b));
    }

    @Test
    public void testCalculateNumberOfStudents() {
        assertEquals(centuryI14a.getNumberOfStudents(), centuryI14a.calculateNumberOfStudents());

        assertEquals((centuryI14a.getNumberOfStudents() + centuryI14b.getNumberOfStudents()), manipleI.calculateNumberOfStudents());
        assertEquals((centuryW14a.getNumberOfStudents() + centuryW14b.getNumberOfStudents()), manipleW.calculateNumberOfStudents());

        assertEquals((manipleI.calculateNumberOfStudents() + manipleW.calculateNumberOfStudents()), cohort14.calculateNumberOfStudents());
    }
}
