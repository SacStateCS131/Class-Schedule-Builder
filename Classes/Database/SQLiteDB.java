import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class SQLiteDB
{
    private ResultSetMetaData mRSMeta;
    private String msURL;
    private Connection mConn = null;
    private Statement mStmt;
    private ResultSet mRS;

    public SQLiteDB() { }

    public SQLiteDB(String sURL)
    {
        msURL = sURL;
        try
        {
            mConn = DriverManager.getConnection(msURL);
            System.out.println("Database connection Successful.");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Release()
    {
        mConn   = null;
        mStmt   = null;
        msURL   = null;
        mRS     = null;
        mRSMeta = null;
    }

    public boolean Execute(String sSQL, StringBuffer sRows, StringBuffer sCols)
    {
        int iColCnt;

        sRows.delete(0, sRows.length());
        sCols.delete(0, sCols.length());

        try
        {
            mStmt = mConn.createStatement();
            mRS = mStmt.executeQuery(sSQL);

            if(mRS.isClosed()) //CHECK THIS
                return false;

            mRSMeta = mRS.getMetaData();
            iColCnt = mRSMeta.getColumnCount();

            while(mRS.next())
            {
                for(int i = 1; i < iColCnt; i++)
                {
                    Object value = mRS.getObject(i);
                    System.out.println(value + "\n");
                }
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return true;
    }

/*    public boolean Execute(String sSQL*//*, Object here*//*)
    {
        return true;
    }*/

    public boolean Execute(String sSQL)
    {

        return true;
    }
}
