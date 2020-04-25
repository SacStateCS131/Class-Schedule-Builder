import java.util.ArrayList;

public class EntryPoint
{
    public static void main(String[] args)
    {
        ArrayList<Course> courseList = new ArrayList<>();

        String sPath = "jdbc:sqlite:C:/Users/naume/Documents/SQLite/Classes.db";
        SQLiteDB sql = new SQLiteDB(sPath);
        
        Scanner input = new Scanner(System.in);
        int selection;

        do
        {
            System.out.println("Select an option:");
            System.out.println("1) Add course to schedule");
            System.out.println("2) Print schedules");
            selection = input.nextInt();
            input.nextLine();//skip \n after int input.
            if(selection == 1)
            {
                System.out.println("Enter subject:");
                String subject = input.nextLine().toUpperCase();
                System.out.println("Enter course number:");
                String number = input.nextLine();
                String query = "SELECT * FROM CourseList WHERE Subject='"+subject+"' AND Course_No='"+number+"';";
                if(sql.Execute(query, courseList))
                {
                    System.out.println(subject + " " + number + " added to schedule");
                    //Planner.addCourses(courseList)
                }
                else
                {
                    System.out.println("No courses matching: " + subject + " " + number);
                }
            }
        }while(selection != 2);
        //Planner.print()
    }
}
