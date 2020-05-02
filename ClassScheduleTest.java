import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;


public class ClassScheduleTest 
{    
    //Set this path to wherever you are storing Classes.db
    String path = "C:/Classes.db";
    
    @Test
    public void goodInstanceTest()
    {
        SQLiteDB sql = SQLiteDB.getInstance();
        assertEquals("correct path: ", true, sql.Init(path));
    }
    
    @Test
    public void badInstanceTest()
    {
        String badPath = "skadkdsajdsa";
        SQLiteDB badSql = SQLiteDB.getInstance();
        assertEquals("bad path: ", false, badSql.Init(badPath));
        
        String emptyPath = "";
        SQLiteDB emptySql = SQLiteDB.getInstance();
        assertEquals("empty path", false, emptySql.Init(emptyPath));
    }
    
    @Test
    public void emptyExecute()
    {
        ArrayList<Course> courseList = new ArrayList<>();
        SQLiteDB sql = SQLiteDB.getInstance();
        assertEquals("Released SQL", false, sql.Execute("Select * from CourseList;", courseList));
    }
    
    @Test
    public void fakeFileExecute()
    {
        ArrayList<Course> courseList = new ArrayList<>();
        String fakePath = "C:/Users/codyt/Desktop/a.db";
        SQLiteDB sql = SQLiteDB.getInstance();
        assertEquals("fake path Init", true, sql.Init(fakePath));
        assertEquals("fake path Execute", false, sql.Execute("Select * from CourseList;", courseList));
        
        SQLiteDB sql2 = SQLiteDB.getInstance();
        String textPath = "C:/Users/codyt/Desktop/text";
        assertEquals("text path init", true, sql.Init(textPath));
    }
    
    @Test
    public void releaseTest()
    {
        ArrayList<Course> courseList = new ArrayList<>();
        Course newCourse = new Course("subject", 53232, "description", 213, "courseSection", "MoTuWeThFrSaSu 8:00AM - 9:00AM", "rooms", "carl", "8/04/22 - 8/33/24");
        Course course = new Course(newCourse);
        courseList.add(newCourse);
        SQLiteDB sql = SQLiteDB.getInstance();
        sql.Init(path);
        sql.Release();
        assertEquals("Released SQL", false, sql.Execute("Select * from CourseList;", courseList));
    }
    
    @Test
    public void goodExecute()
    {
        Course newCourse = new Course("subject", 53232, "description", 213, "courseSection", "MoTuWeThFrSaSu 8:00AM - 9:00AM", "rooms\nrooms", "carl\ncarl2", "8/04/22 - 8/33/24");
        Course lateCourse = new Course("subject", 53232, "description", 213, "courseSection", "MoTuWeThFrSaSu 7:30PM - 9:30PM", "rooms", "carl", "8/04/22 - 8/33/24");
        Course tbaCourse = new Course("subject", 53232, "description", 213, "courseSection", "", "rooms", "carl", "8/04/22 - 8/33/24");
        Course course = new Course(newCourse);
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(newCourse);
        courseList.add(course);
        course.printCourse();
        tbaCourse.printCourse();
        lateCourse.printCourse();
        SQLiteDB sql = SQLiteDB.getInstance();
        sql.Init(path);
        assertEquals("Released SQL", true, sql.Execute("Select * from CourseList;", courseList));
        courseList.clear();
        assertEquals("Released SQL", true, sql.Execute("Select * from CourseList;", courseList));
    }
    
    @Test
    public void conflictCheck()
    {
        Course newCourse = new Course("subject", 53232, "description", 213, "courseSection", "MoTuWeThFrSa 8:00AM - 9:00AM", "rooms\nrooms", "carl\ncarl2", "8/04/22 - 8/33/24");
        Course newCourse2 = new Course("dbject", 57232, "Descrion", 211, "Section", "MoTuWeThFrSu 8:45AM - 9:30AM", "roomss\nrooms2", "carls\ncarl22", "8/04/22 - 8/33/24");
        Course newCourse4 = new Course("diffSubject", 59232, "Dehion", 211, "Secon", "MoTuWeFrSaSu 7:30AM - 8:44AM", "roomss\nrooms2", "carls\ncarl22", "8/04/22 - 8/33/24");
        Course newCourse3 = new Course("coolSubject", 56232, "shions", 221, "Section2", "MoTuWeThSaSu 9:10AM - 10:30AM", "ro1omss\nr3ooms2", "ca1rls\nca3rl22", "8/04/22 - 8/33/24");
        Course noTime = new Course("notime", 56232, "shions", 221, "Section2", "", "ro1omss\nr3ooms2", "ca1rls\nca3rl22", "8/04/22 - 8/33/24");
        Course course1 = new Course(newCourse);
        Schedule shedJewel = new Schedule();
        assertEquals("first add", true, shedJewel.addCourse(newCourse));
        assertEquals("first add duped", true, shedJewel.addCourse(course1));
        assertEquals("second add", false, shedJewel.addCourse(newCourse2));
        assertEquals("third add", true, shedJewel.addCourse(newCourse3));
        assertEquals("fourth add", false, shedJewel.addCourse(newCourse4));        assertEquals("noTime add", true, shedJewel.addCourse(noTime));
        shedJewel.display();
    }
    
    @Test
    public void courseGetsCheck()
    {
        Course newCourse = new Course("subject", 53232, "description", 213, "courseSection", "MoTuWeThFrSa 8:00AM - 9:00AM", "room", "carl", "8/04/22 - 8/33/24");
        assertEquals("getName()", "subject", newCourse.getName());
        assertEquals("getNumber()", 53232, newCourse.getNumber());
        assertEquals("getDescription()", "description", newCourse.getDescription());
        assertEquals("getId()", 213, newCourse.getId());
        assertEquals("getSection()", "courseSection", newCourse.getSection());
        //going to have a size of 1
        TimeSlot[] timey = newCourse.getTimeSlots();
        TimeSlot classTime = timey[0];
        TimeSlot testTime = new TimeSlot("MoTuWeThFrSa 8:00AM - 9:00AM");
        
        assertEquals("checkDayCode()", testTime.getDayCode(), classTime.getDayCode());
        assertEquals("checkStartMin()", testTime.getStartMin(), classTime.getStartMin());
        assertEquals("getStartHour()", testTime.getStartHour(), classTime.getStartHour());
        assertEquals("getEndMin()", testTime.getEndMin(), classTime.getEndMin());
        assertEquals("getEndHour()", testTime.getEndHour(), classTime.getEndHour());
        String str = "room";
        String[] strs = newCourse.getRooms();
        assertEquals("getRooms()", str, strs[0]);
        String name = "carl";
        String[] names = newCourse.getInstructors();
        assertEquals("getInstructors()", name, names[0]);
        assertEquals("getDates()", "8/04/22 - 8/33/24", newCourse.getDates());
    }

    @Test
    public void anotherTimeCheck()
    {
        Course newCourse = new Course("subject", 53232, "description", 213, "courseSection", "Mo 8:00AM - 9:00AM", "room", "carl", "8/04/22 - 8/33/24");
        Course newCourseConflict = new Course("diffsubject", 21232, "descript", 223, "caution", "Mo 8:01AM - 8:30AM", "room2", "dumpy", "8/04/22 - 8/33/24");
        Schedule shedjewel = new Schedule();
        shedjewel.addCourse(newCourse);
        assertEquals("shouldBeConflict", false, shedjewel.addCourse(newCourseConflict));
    }
    
    @Test
    public void scheduleIteratorTest()
    {
        ArrayList<Course> courseList = new ArrayList<>();
        Schedule shedjewel = new Schedule();
        SQLiteDB sql = SQLiteDB.getInstance();
        sql.Init(path);
        if(sql.Execute("Select * from CourseList;", courseList))
        {
            for(int i = 0; i < courseList.size(); i++)
            {
                shedjewel.addCourse(courseList.get(i));
            }
        }
        
        assertEquals("currentItem()", courseList.get(0), shedjewel.currentItem());
        shedjewel.next();
        assertEquals("next()", courseList.get(1), shedjewel.currentItem());
        assertEquals("hasNext()", true, shedjewel.hasNext());
        while(shedjewel.hasNext())
            shedjewel.next();
        assertEquals("hasNext()", false, shedjewel.hasNext());
        shedjewel.reset();
        assertEquals("hasNext()", true, shedjewel.hasNext());
    }
}
