package model;

import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.model.Maniple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GroupTest {

    private final Cohort cohort14 = new Cohort("14");
    private final Maniple manipleI = new Maniple("I14");
    private final Maniple manipleW = new Maniple("W14");
    private final Century centuryI14a = new Century("I14a", 35);
    private final Century centuryI14b = new Century("I14b", 30);
    private final Century centuryW14a = new Century("W14a", 40);
    private final Century centuryW14b = new Century("W14b", 35);

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

    @Test
    public void testHasSubGroup() {
        cohort14.getManiples().forEach(maniple -> assertTrue(cohort14.hasSubGroup(maniple)));
        cohort14.getManiples().forEach(maniple ->
                maniple.getCenturies().forEach(century -> assertTrue(cohort14.hasSubGroup(century))));
        manipleI.getCenturies().forEach(century -> {
            assertTrue(manipleI.hasSubGroup(century));
            assertFalse(manipleW.hasSubGroup(century));
        });
        assertFalse(centuryI14a.hasSubGroup(manipleI));
        assertFalse(centuryI14a.hasSubGroup(cohort14));
    }
}
