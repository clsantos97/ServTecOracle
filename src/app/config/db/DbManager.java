package app.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;
/**
 *
 * @author Carlos
 */
public class DbManager {

    private static DbManager instance = null;
    private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DBUSER = "MANAGER";
    private static final String DBPASS = "1234";
    private Connection dbconn;

    private DbManager() {

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dbconn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            if (dbconn != null) {
                System.out.println("Connected with connection #2");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DbManager getInstance() {
      if(instance == null) {
         instance = new DbManager();
      }
      return instance;
   }
    
    public Connection getDbconn() {
        return dbconn;
    }
    
    

}
