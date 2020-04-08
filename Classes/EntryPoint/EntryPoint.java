import java.util.ArrayList;

public class EntryPoint
{
    public static void main(String[] args)
    {
        ArrayList<Course> courseList = new ArrayList<>();

        String sPath = "jdbc:sqlite:C:/Users/naume/Documents/SQLite/Classes.db";
        SQLiteDB sql = new SQLiteDB(sPath);
        sql.Execute("Select * from CourseList;", courseList);

        System.out.println("Import Successful.");
    }
}
