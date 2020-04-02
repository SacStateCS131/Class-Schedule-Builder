import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
    private String msURL;
    private Connection mConn = null;
    private Statement mStmt;
    private ResultSet RS;

    public SQLiteDB() {
    }

    public SQLiteDB(String sURL) {
        msURL = sURL;
        try {
            mConn = DriverManager.getConnection(msURL);
            System.out.println("Connection Successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
