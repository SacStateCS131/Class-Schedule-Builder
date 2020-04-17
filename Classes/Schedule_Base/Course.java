class Course
{
    private String subject;
    private int courseNumber;
    private String description;
    private int id;
    private String courseSection;
    private String[] rooms;
    private String[] instructors;
    private TimeSlot[] dayAndTime;
    private String meetingDates;

    /**
    * Course constructor.
    * @param subject String that contains course subject
    *                   ex: "CSC"
    * @param courseNumber Integer course number
    *                   ex: 131
    * @param description String describes what the course is
    *                   ex: "Introduction to Programming Logic"
    * @param courseId Integer unique ID for course
    * @param courseSection String contains section of course
    *                   ex: "01-ACT six wk 1"
    * @param rooms String[] that contains the course room(s)
    * @param instructors String[] contains professor name(s) for the course
    *                   ex: "Devin Cook"
    * @param dayAndTime String that contains day and time data
    *                   ex: "MoWe 8:00AM - 9:00AM"
    * @param meetingDates String contains the start and end dates
    *                   ex: {"5/26/2020", "7/02/2020"}
    */
    Course(String subject, int courseNumber, String description, int courseId, String courseSection, String dayAndTime, String rooms, String instructors, String meetingDates)
    {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.description = description;
        this.id = courseId;
        this.courseSection = courseSection;
        String[] dayAndTimeList = dayAndTime.split("\n");
        this.rooms = rooms.split("\n");
        this.instructors = instructors.split("\n");
        this.dayAndTime = new TimeSlot[dayAndTimeList.length];
        this.meetingDates = meetingDates;
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
        this.courseSection = course.courseSection;
        this.rooms = course.rooms;
        this.instructors = course.instructors;
        this.dayAndTime = new TimeSlot[course.dayAndTime.length];
        this.meetingDates = course.meetingDates;
        for(int i = 0; i < course.dayAndTime.length; i++)
        {
            this.dayAndTime[i] = new TimeSlot(course.dayAndTime[i]);
        }
    }

    public String getName(){ return this.subject; }

    public int getNumber() { return this.courseNumber; }
    
    public String getDescription() { return this.description; }
    
    public int getId(){ return this.id; }
    
    public String getSection() { return this.courseSection; }
    
    public TimeSlot[] getTimeSlots(){ return this.dayAndTime; }
    
    public String[] getRooms() { return this.rooms; }
    
    public String[] getInstructors() { return this.instructors; }
    
    public String getDates() { return this.meetingDates; }

    /**
    * Name: CSC 10
    * description: Introduction to Programming Logic
    * ID: 50472
    * Section: 02-DIS Six Wk 1
    * Days: Monday
    *       Wednesday
    *       Friday
    * Start Time: 10:30
    * End Time: 11:55
    * Rooms: Web Online
    * Instructors: Devin Cook
    * Meeting Dates:
    * 05/26/2020 - 07/02/2020
    */
    public void printCourse()
    {
        System.out.println("Course Name: " + this.subject + this.courseNumber);
        System.out.println("description: " + this.description);
        System.out.println("ID: " + this.id);
        System.out.println("Section: " + this.courseSection);
        for(TimeSlot dayAndTime : this.dayAndTime)
        {
            String[] days = dayAndTime.getDays();
            System.out.println("Days: " + days[0]);
            for(int i = 1; i < days.length; i++)
            {
                System.out.println("      " + days[i]);
            }
            String[] extraZeroes = {"", ""};
            if(dayAndTime.getStartMin() < 10)
            {
                extraZeroes[0] = "0";
            }
            if(dayAndTime.getEndMin() < 10)
            {
                extraZeroes[1] = "0";
            }
            System.out.println("Start Time: " + 
                            dayAndTime.getStartHour() +
                            ":" + extraZeroes[0] +
                            dayAndTime.getStartMin());
            System.out.println("End Time: " + 
                            dayAndTime.getEndHour() +
                            ":" + extraZeroes[1] +
                            dayAndTime.getEndMin());
        }
        System.out.println("Rooms: " + this.rooms[0]);
        for(int i = 1; i < this.rooms.length; i++)
        {
        	System.out.println("       " + this.rooms[i]);
        }
        System.out.println("Instructors: " + this.instructors[0]);
        for(int i = 1; i < this.instructors.length; i++)
        {
        	System.out.println("             " + this.instructors[i]);
        }
        System.out.println("Meeting Dates:");
        System.out.println(this.meetingDates);
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
