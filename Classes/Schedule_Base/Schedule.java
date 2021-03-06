import java.util.ArrayList;

class Schedule implements Iterator<Course>
{
    private ArrayList<Course> courses;
    int position;
    
    /**
    * default Schedule constructor
    */
    Schedule()
    {
        this.courses = new ArrayList<Course>();
    }

    Schedule(Schedule s)
    {
        this.courses  = s.courses;
        this.courses  = new ArrayList<Course>(s.courses);
        this.position = s.position;
    }

    /**
     * Iterator reset override
     */
    @Override
    public void reset()
    {
        position = 0;
    }

    /**
     * Iterator next override
     */
    @Override
    public Course next()
    {
        if(this.hasNext())
            return this.courses.get(position++);
        else
            return null;
    }

    /**
     * Iterator currentItem override
     */
    @Override
    public Course currentItem()
    {
        return this.courses.get(position);
    }

    /**
     * Iterator hasNext override
     */
    @Override
    public boolean hasNext()
    {
        if(position >= this.courses.size())
            return false;
        return true;
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
        this.reset();

        while(this.hasNext())
        {
            this.next().printCourse();
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
        this.reset();

        while(this.hasNext())
        {
            if(this.next().checkConflict(course))
                return true;
        }

        //no conflict detected
        return false;
    }
}