class Course
{
    private TimeSlot[] dayAndTime;
    private String name;
    private int id;

    /**
    * Course constructor.
    * @param courseName String that contains course subject and number
    *                   ex: "CSC 131"
    * @param courseId Integer unique ID for course
    * @param dayAndTime String that contains day and time data
    *                   ex: "MoWe 8:00AM - 9:00AM"
    */
    Course(String courseName, int courseId, String dayAndTime)
    {
        this.name = courseName;
        this.id = courseId;
        String[] dayAndTimeList = dayAndTime.split("\n");
        this.dayAndTime = new TimeSlot[dayAndTimeList.length];
        for(int i = 0; i < dayAndTimeList.length; i++)
        {
            this.dayAndTime[i] = new TimeSlot(dayAndTimeList[i]);
        }
    }

    /**
    * Course copy constructor.
    * @param course Course to be copied
    */
    Course(Course course)
    {
        this.name = course.name;
        this.id = course.id;
        this.dayAndTime = new TimeSlot[course.dayAndTime.length];
        for(int i = 0; i < course.dayAndTime.length; i++)
        {
            this.dayAndTime[i] = new TimeSlot(course.dayAndTime[i]);
        }
    }

    public TimeSlot[] getTimeSlots(){ return this.dayAndTime; }

    public String getName(){ return this.name; }

    public int getId(){ return this.id; }

    /**
    * Prints out the Course information in the following format:
    *   Name: CSC 131
    *   ID: 31407
    *   Days: Monday
    *         Wednesday
    *   Start Time: 17:30
    *   End Time: 18:45
    */
    public void printCourse()
    {
        System.out.println("Name: " + this.name);
        System.out.println("ID: " + this.id);
        for(TimeSlot dayAndTime : this.dayAndTime)
        {
            String[] days = dayAndTime.getDays();
            System.out.println("Days: " + days[0]);
            for(int i = 1; i < days.length; i++)
            {
                System.out.println("      " + days[i]);
            }
            System.out.println("Start Time: " + 
                            dayAndTime.getStartHour() +
                            ":" +
                            dayAndTime.getStartMin());
            System.out.println("End Time: " + 
                            dayAndTime.getEndHour() +
                            ":" +
                            dayAndTime.getEndMin());
        }
    }

    /**
    * Checks two Courses for a TimeSlot conflict.
    * @param course Course object to be compared to this object.
    * @return boolean value true if conflict exsists, otherwise false.
    */
    public boolean checkConflict(Course course)
    {
        for(TimeSlot thisTimeSlot : this.dayAndTime)
        {
            for(TimeSlot courseTimeSlot : course.dayAndTime)
            {
                if(thisTimeSlot.checkConflict(courseTimeSlot))
                {
                    //conflict detected.
                    return true;
                }
            }
        }
        //conflict not detected
        return false;
    }
}