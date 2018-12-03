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
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            //System.out.println(dr.getStart().toString() + " " + dr.getEnd().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsTrueTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertTrue(dr.contains(new DateRange("19980118T231000Z", "19980118T231500Z")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertFalse(dr.contains(new DateRange("20000118T230000Z", "20000118T231500Z")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseEdgeOfRangeBothTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertFalse(dr.contains(new DateRange("19980118T230000Z", "19980118T240000Z")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseEdgeOfRangeStartOnlyTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertFalse(dr.contains(new DateRange("19980118T230000Z", "19980118T231000Z")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsFalseEdgeOfRangeEndOnlyTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertFalse(dr.contains(new DateRange("19980118T231000Z", "19980118T240000Z")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeStartIntersectsTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertEquals(dr.intersects(new DateRange("19980118T231000Z", "20000118T240000Z")), 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeEndIntersectsTrueTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertEquals(dr.intersects(new DateRange("19000118T230000Z", "19980118T231000Z")), 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void dateRangeIntersectsOutOfRangeFailureTest(){
        try {
            DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");
            assertEquals(dr.intersects(new DateRange("19000118T230000Z", "20000118T230500Z")), 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateRangeContainsRemovalFromListWithOneElementTest(){
        try {
        	DateRange dr = new DateRange("19980118T230000Z", "19980118T240000Z");

			ArrayList<DateRange> dates = new ArrayList<>();
			dates.add(dr);

			DateRange dr1 = new DateRange("19980118T231000Z", "19980118T235000Z");
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