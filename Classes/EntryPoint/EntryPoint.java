import java.util.ArrayList;
import java.util.Scanner;

public class EntryPoint
{
    public static void main(String[] args)
    {
        ArrayList<Course> courseList = new ArrayList<Course>();
        Planner planner = new Planner();

        String sPath = "jdbc:sqlite:C:\\Users\\Alexander\\IdeaProjects\\SQLite\\Classes.db";
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
                    planner.addCourses(courseList);
                }
                else
                {
                    System.out.println("No courses matching: " + subject + " " + number);
                }
            }
        }while(selection != 2);
        planner.printSchedule();
    }
}