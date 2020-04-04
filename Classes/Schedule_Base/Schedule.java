import java.util.ArrayList;

class Schedule
{
    private ArrayList<Course> courses;
    
    /**
    * defualt Schedule constructor
    */
    Schedule()
    {
        this.courses = new ArrayList<Course>();
    }

    /**
    * Adds course to Schedule if no conflict exsists.
    * @param course Course to add to Schedule.
    * @return boolean true if add successfully, otherwise false.
    */
    public boolean addCourse(Course course)
    {
        if(this.checkConflict(course) == false)
        {
            // No conflict detected. Add course.
            courses.add(course);
            return true;
        }
        else
        {
            // Conflict detected.
            return false;
        }
    }

    /**
    * Prints the Schedule to the console one course at a time.
    */
    public void display()
    {
        for(int i = 0; i < courses.size(); i++)
        {
            courses.get(i).printCourse();
            System.out.println();
        }
    }

    /**
    * Checks if course conflicts with any course already in Schedule.
    * @param course Course to check for conflict with Schedule.
    * @return boolean true if conflict exsists, otherwise false.
    */
    private boolean checkConflict(Course course)
    {
        for(int i = 0; i < courses.size(); i++)
        {
            if(courses.get(i).getTimeSlot().conflict(course.getTimeSlot()))
                return true;
        }
        return false;
    }
}