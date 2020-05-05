import java.util.ArrayList;

public class Planner
{
    private ArrayList<Schedule> scheduleList;

    Planner()
    {
        scheduleList = new ArrayList<Schedule>();
        scheduleList.add(new Schedule());
    }

    Planner(Planner p)
    {
        scheduleList = p.scheduleList;
    }

    public void printSchedule()
    {
        for (Schedule s : scheduleList)
        {
            System.out.println("========================================");
            s.display();
            System.out.println("========================================");
        }

    }

    public void addCourses(ArrayList<Course> courses )
    {
        ArrayList<Schedule> newList = new ArrayList<Schedule>();
        for (Course c : courses )
        {
            for (Schedule s : scheduleList)
            {
                Schedule temp = new Schedule(s);
                if(temp.addCourse(c))
                {
                    newList.add(temp);
                }
            }
        }
        scheduleList = newList;
    }



}