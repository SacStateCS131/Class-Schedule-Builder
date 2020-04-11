class Course
{
    private String subject;
    private int courseNumber;
    private String description;
    private int id;
    private String room;
    private String instructor;
    private TimeSlot[] dayAndTime;

    /**
    * Course constructor.
    * @param subject String that contains course subject
    *                   ex: "CSC"
    * @param courseNumber Integer course number
    *                   ex: 131
    * @param courseId Integer unique ID for course
    * @param description String describes what the course is
    *                   ex: "Introduction to Programming Logic"
    * @param room String that contains the course room
    * @param instructor String contains professor's name for the course
    *                   ex: "Devin Cook"
    * @param dayAndTime String that contains day and time data
    *                   ex: "MoWe 8:00AM - 9:00AM"
    */
    Course(String subject, int courseNumber, String description, int courseId, String dayAndTime, String room, String instructor)
    {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.description = description;
        this.id = courseId;
        String[] dayAndTimeList = dayAndTime.split("\n");
        this.room = room;
        this.instructor = instructor;
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
        this.subject = course.subject;
        this.courseNumber = course.courseNumber;
        this.description = course.description;
        this.id = course.id;
        this.room = course.room;
        this.instructor = course.instructor;
        this.dayAndTime = new TimeSlot[course.dayAndTime.length];
        for(int i = 0; i < course.dayAndTime.length; i++)
        {
            this.dayAndTime[i] = new TimeSlot(course.dayAndTime[i]);
        }
    }
    
    /*
     *      private TimeSlot[] dayAndTime;
		    private String subject;
		    private int courseNumber;
		    private int id;
		    private String room;
		    private String instructor;
     */

    public String getName(){ return this.subject; }

    public int getNumber() { return this.courseNumber; }
    
    public String getDescription() { return this.description; }
    
    public int getId(){ return this.id; }
    
    public TimeSlot[] getTimeSlots(){ return this.dayAndTime; }
    
    public String getRoom() { return this.room; }
    
    public String getTeacher() { return this.instructor; }

    /**
    * Prints out the Course information in the following format:
    *   subject: CSC131
    *   ID: 31407
    *   description: Computer Software Engineering
    *   Days: Monday
    *         Wednesday
    *   Start Time: 17:30
    *   End Time: 18:45
    *   Room: Eureka 121
    *   Instructor: Jagannadha Chidella
    */
    public void printCourse()
    {
        System.out.println("subject: " + this.subject + this.courseNumber);
        System.out.println("ID: " + this.id);
        System.out.println("description: " + this.description);
        for(TimeSlot dayAndTime : this.dayAndTime)
        {
            String[] days = dayAndTime.getDays();
            System.out.println("Days: " + days[0]);
            for(int i = 1; i < days.length; i++)
            {
                System.out.println("      " + days[i]);
            }
            String extraZero = "";
            if(dayAndTime.getEndMin() < 10)
            {
                extraZero = "0";
            }
            System.out.println("Start Time: " + 
                            dayAndTime.getStartHour() +
                            ":" + extraZero +
                            dayAndTime.getStartMin());
            System.out.println("End Time: " + 
                            dayAndTime.getEndHour() +
                            ":" + extraZero +
                            dayAndTime.getEndMin());
        }
        System.out.println("Room: " + this.room);
        System.out.println("Instructor: " + this.instructor);
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
