import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class TimeSlot
{
    private byte dayCode;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
    * TimeSlot constructor.
    * @param dayAndTime String that contains day and time data
    *                   ex: "MoWe 8:00AM - 9:00AM"
    */
    TimeSlot(String dayAndTime)
    {
        String dayAndTimeRegex = "(?<Mo>Mo)?(?<Tu>Tu)?(?<We>We)?(?<Th>Th)?(?<Fr>Fr)?(?<Sa>Sa)?(?<Su>Su)?(\\s*(?<startTime>\\d+:\\d+[A-Z]+)\\s+-\\s+(?<endTime>\\d+:\\d+[A-Z]+))?";
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
        if(dayCode != 0) {
            startTime = LocalTime.parse(dayAndTimeMatch.group("startTime"),
                    DateTimeFormatter.ofPattern("h:mma"));
            endTime = LocalTime.parse(dayAndTimeMatch.group("endTime"),
                    DateTimeFormatter.ofPattern("h:mma"));
        }
        else
        {
            startTime = null;
            endTime = null;
        }
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

    public byte getDayCode(){ return this.dayCode; }

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
        if((this.dayCode & 0x01) != 0)
        {
            days[i] = "Monday";
            i++;
        }
        if((this.dayCode & 0x02) != 0)
        {
            days[i] = "Tuesday";
            i++;
        }
        if((this.dayCode & 0x04) != 0)
        {
            days[i] = "Wednesday";
            i++;
        }
        if((this.dayCode & 0x08) != 0)
        {
            days[i] = "Thursday";
            i++;
        }
        if((this.dayCode & 0x10) != 0)
        {
            days[i] = "Friday";
            i++;
        }
        if((this.dayCode & 0x20) != 0)
        {
            days[i] = "Saturday";
            i++;
        }
        if((this.dayCode & 0x40) != 0)
        {
            days[i] = "Sunday";
        }
        return days;
    }

    public int getStartHour(){ return this.startTime.getHour(); }

    public int getStartMin(){ return this.startTime.getMinute(); }

    public int getEndHour(){ return this.endTime.getHour(); }

    public int getEndMin(){ return this.endTime.getMinute(); }

    /**
    * Checks two TimeSlots for a conflict.
    * @param dayAndTime TimeSlot object to be compared to this object.
    * @return boolean value true if conflict exsists, otherwise false.
    */
    public boolean checkConflict(TimeSlot dayAndTime)
    {
        //bitwise & used to check dayCodes for matching bits
        if((this.dayCode & dayAndTime.dayCode) != 0)
        {
            if((this.startTime.compareTo(dayAndTime.startTime) > 0 && 
                this.startTime.compareTo(dayAndTime.endTime) < 0) ||
               (this.endTime.compareTo(dayAndTime.startTime) > 0 &&
                this.endTime.compareTo(dayAndTime.endTime) < 0))
            {
                //conflict found
                return true;
            }
        }
        //no conflict found
        return false;
    }
}