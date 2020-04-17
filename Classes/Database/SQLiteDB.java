import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class SQLiteDB
{
    private ResultSetMetaData mRSMeta;
    private String msURL;
    private Connection mConn = null;
    private Statement mStmt;
    private ResultSet mRS;

    /**
     * SQLiteDB constructor.
     * Expand later.
     */
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

    /**
     * Un-initialises the SQLite object. Releases all handles.
     */
    public void Release()
    {
        mConn   = null;
        mStmt   = null;
        msURL   = null;
        mRS     = null;
        mRSMeta = null;
    }

    /**
     * Executes the supplied SQL statement and fills the provided
     * list of Course objects.
     * @param sSQL Input Select SQL statement
     *                   ex: "SELECT * FROM TABLE"
     * @param courseList Arraylist of course objects. It is emptied
     *                   at the beginning of execution.
     * @return Returns true if all goes well. False if the
     *         recordset is either incomplete or empty.
     */
    public boolean Execute(String sSQL, ArrayList<Course> courseList)
    {
        //int iColCnt;

        if(!courseList.isEmpty())
        {
            courseList.clear();
        }

        try
        {
            mStmt = mConn.createStatement();
            mRS = mStmt.executeQuery(sSQL);

            if(mRS.isClosed())
                return false;

            mRSMeta = mRS.getMetaData();
            //iColCnt = mRSMeta.getColumnCount();

            while(mRS.next())
            {
                courseList.add(new Course(mRS.getString(1), mRS.getInt(2), mRS.getString(3), mRS.getInt(4),
                               mRS.getString(6), mRS.getString(7), mRS.getString(8), mRS.getString(9),
                               mRS.getString(10)));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return true;
    }

    /**
     * Executes the supplied SQL statement. Intended for insert
     * statements.
     * TODO: Impliment logic if necessary.
     * @param sSQL Input Insert SQL statement
     *                   ex: "INSERT () INTO TABLE()"
     * @return Returns true if the insert is successful. False
     * if the insert statement fails.
     */
    public boolean Execute(String sSQL)
    {
        //reserved for if we ever need to do an insert
        return true;
    }
}
