public class EntryPoint
{
    public static void main(String[] args)
    {
        StringBuffer sRows, sCols;
        sRows = new StringBuffer();
        sCols = new StringBuffer();

        String sPath = "jdbc:sqlite:C:/Users/naume/Documents/SQLite/Classes.db";
        SQLiteDB sql = new SQLiteDB(sPath);
        sql.Execute("Select * from Classes;", sRows, sCols);
    }
}
