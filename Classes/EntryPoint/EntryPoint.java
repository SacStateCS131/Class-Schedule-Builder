import java.util.ArrayList;

public class EntryPoint
{
    public static void main(String[] args)
    {
        ArrayList<Course> courseList = new ArrayList<>();

        String sPath = "C:/Users/naume/Documents/SQLite/Classes.db";
        SQLiteDB sql = SQLiteDB.getInstance();
        sql.Init(sPath);

        if(sql.Execute("Select * from CourseList;", courseList))
            System.out.println("Import Successful.");
    }
}
