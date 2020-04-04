import java.util.regex.*;

class TimeSlot
{
    private byte dayCode;
    private int startTime;
    private int endTime;

    /**
    * TimeSlot constructor.
    * @param dayAndTime String that contains day and time data
    *                   ex: "MoWe 8:00AM - 9:00AM"
    */
    TimeSlot(String dayAndTime)
    {
        String dayAndTimeRegex = "(?<Mo>Mo)?(?<Tu>Tu)?(?<We>We)?(?<Th>Th)?(?<Fr>Fr)?(?<Sa>Sa)?(?<Su>Su)?\\s*(?<startHour>\\d+):(?<startMinute>\\d+)(?<startMeridiem>[A-Z]+)\\s+-\\s+(?<endHour>\\d+):(?<endMinute>\\d+)(?<endMeridiem>[A-Z]+)";
        Pattern dayAndTimePattern = Pattern.compile(dayAndTimeRegex);
        Matcher dayAndTimeMatch = dayAndTimePattern.matcher(dayAndTime);
        dayAndTimeMatch.find();
        this.dayCode = 0;
        if(dayAndTimeMatch.group("Mo") != null)
            this.dayCode |= 0x01;
        if(dayAndTimeMatch.group("Tu") != null)
            this.dayCode |= 0x02;
        if(dayAndTimeMatch.group("We") != null)
            this.dayCode |= 0x04;
        if(dayAndTimeMatch.group("Th") != null)
            this.dayCode |= 0x08;
        if(dayAndTimeMatch.group("Fr") != null)
            this.dayCode |= 0x10;
        if(dayAndTimeMatch.group("Sa") != null)
            this.dayCode |= 0x20;
        if(dayAndTimeMatch.group("Su") != null)
            this.dayCode |= 0x40;
        this.startTime = (Integer.parseInt(dayAndTimeMatch.group("startHour"))*60) + (Integer.parseInt(dayAndTimeMatch.group("startMinute")));
        if(dayAndTimeMatch.group("startMeridiem").equals("PM") && 
           dayAndTimeMatch.group("startHour") != "12")
            this.startTime += 60*12;
        this.endTime = (Integer.parseInt(dayAndTimeMatch.group("endHour"))*60) + (Integer.parseInt(dayAndTimeMatch.group("endMinute")));
        if(dayAndTimeMatch.group("endMeridiem").equals("PM")&& 
           dayAndTimeMatch.group("endHour") != "12")
            this.endTime += 60*12;
    }

    /**
    * TimeSlot copy constructor.
    * @param dayAndTime TimeSlot to be copied.
    */
    TimeSlot(TimeSlot dayAndTime)
    {
        this.dayCode = dayAndTime.dayCode;
        this.startTime = dayAndTime.startTime;
        this.endTime = dayAndTime.endTime;
    }

    public byte getDayCode(){ return dayCode; }

    public String[] getDays()
    {
        //count days
        int dayCount = 0;
        for(int i = 0x01; i < 0x80; i<<=1)
        {
            if((dayCode & i) != 0)
                dayCount++;
        }
        String[] days = new String[dayCount];
        int i = 0;
        if((dayCode & 0x01) != 0)
        {
            days[i] = "Monday";
            i++;
        }
        if((dayCode & 0x02) != 0)
        {
            days[i] = "Tuesday";
            i++;
        }
        if((dayCode & 0x04) != 0)
        {
            days[i] = "Wednesday";
            i++;
        }
        if((dayCode & 0x08) != 0)
        {
            days[i] = "Thursday";
            i++;
        }
        if((dayCode & 0x10) != 0)
        {
            days[i] = "Friday";
            i++;
        }
        if((dayCode & 0x20) != 0)
        {
            days[i] = "Saturday";
            i++;
        }
        if((dayCode & 0x40) != 0)
        {
            days[i] = "Sunday";
        }
        return days;
    }

    public int getStartHour(){ return startTime/60; }

    public int getStartMin(){ return startTime%60; }

    public int getEndHour(){ return endTime/60; }

    public int getEndMin(){ return endTime%60; }

    /**
    * Checks two TimeSlots for a conflict.
    * @param dayAndTime TimeSlot object to be compared to this object.
    * @return boolean value true if conflict exsists, otherwise false.
    */
    public boolean conflict(TimeSlot dayAndTime)
    {
        //bitwise & used to check dayCodes for matching bits
        if((this.dayCode & dayAndTime.dayCode) != 0)
        {
            if((this.startTime >= dayAndTime.startTime && 
                this.startTime <= dayAndTime.endTime) ||
               (this.endTime >= dayAndTime.startTime &&
                this.endTime <= dayAndTime.endTime))
            {
                //conflict found
                return true;
            }
        }
        //no conflict found
        return false;
    }
}