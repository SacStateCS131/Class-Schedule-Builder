import java.io.File;
import java.nio.file.InvalidPathException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class SQLiteDB
{
    private ResultSetMetaData mRSMeta = null;
    private String msURL;
    private Connection mConn = null;
    private Statement mStmt;
    private ResultSet mRS;
    private boolean bInit = false;

    private static SQLiteDB mHandle = null;

    /**
     * SQLiteDB constructor.
     * Not used because class is a singleton.
     */
    private SQLiteDB() { }

    /**
     * Singleton function to follow Java standard.
     * @return Will return a SQLite object. Will allocate
     *         the object if one does not exist.
     */
    public static SQLiteDB getInstance()
    {
        if(mHandle == null)
            mHandle = new SQLiteDB();
        return mHandle;
    }

    /**
     * Will attempt to open a connection to a SQLite database
     * using the path provided.
     * @param sURL File path
     *                   ex: "C:\\Database.db"
     * @return Returns true if all goes well. False if the
     *         database cannot be found.
     */
    public boolean Init(String sURL)
    {

        if(!new File(sURL).isFile())
        {
            System.out.println("File does not exist in the provided path: \n" + sURL);
            return false;
        }

        msURL = "jdbc:sqlite:" + sURL;

        try
        {
            mConn = DriverManager.getConnection(msURL);
            System.out.println("Database connection Successful.");
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            msURL = "";
            return false;
        }

        msURL = sURL;
        bInit = true;
        return true;
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
        bInit   = false;
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
        if(!bInit)
        {
            System.out.println("Object not initialized. Cannot execute SQL statement.\n");
            return false;
        }

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

            while(mRS.next())
            {
                courseList.add(new Course(mRS.getString(1), mRS.getInt(2), mRS.getString(3), mRS.getInt(4),
                        mRS.getString(6), mRS.getString(7), mRS.getString(8), mRS.getString(9),
                        mRS.getString(10)));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

//    public boolean Execute(String sSQL)
//    {
//        //reserved for if we ever need to do an insert
//        return true;
//    }
}
