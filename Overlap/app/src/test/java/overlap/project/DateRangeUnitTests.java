package overlap.project;

import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

import overlap.project.scheduler.DateRange;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateRangeUnitTests {
    @Test
    public void createNewDateRangeTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            //System.out.println(dr.getStart().toString() + " " + dr.getEnd().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsTrueTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertTrue(dr.contains(new DateRange("19980118T231000", "19980118T231500")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertFalse(dr.contains(new DateRange("20000118T230000", "20000118T231500")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseEdgeOfRangeBothTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertFalse(dr.contains(new DateRange("19980118T230000", "19980118T240000")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseEdgeOfRangeStartOnlyTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertFalse(dr.contains(new DateRange("19980118T230000", "19980118T231000")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseEdgeOfRangeEndOnlyTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertFalse(dr.contains(new DateRange("19980118T231000", "19980118T240000")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeStartIntersectsTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertEquals(dr.intersects(new DateRange("19980118T231000", "20000118T240000")), 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeEndIntersectsTrueTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertEquals(dr.intersects(new DateRange("19000118T230000", "19980118T231000")), 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void dateRangeIntersectsOutOfRangeFailureTest(){
        try {
            DateRange dr = new DateRange("19980118T230000", "19980118T240000");
            assertEquals(dr.intersects(new DateRange("19000118T230000", "20000118T230500")), 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsRemovalFromListWithOneElementTest(){
        try {
        	DateRange dr = new DateRange("19980118T230000", "19980118T240000");

			ArrayList<DateRange> dates = new ArrayList<>();
			dates.add(dr);

			DateRange dr1 = new DateRange("19980118T231000", "19980118T235000");
			dates = dr1.removeFrom(dates);

			for (DateRange date : dates){
				System.out.println(date.getStart().toString());
				System.out.println(date.getEnd().toString());

			}


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}