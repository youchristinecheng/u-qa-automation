package TestBased;

import sun.jvm.hotspot.oops.Mark;
import TestBased.TestAccountData.Market;
import java.sql.*;


public class DBConnectionHandler {
    private static DBConnectionHandler instance = null;

    private String envEndPt;
    private boolean isdevEnv;
    private Market currentMarket;
//    jdbc:postgresql://rds.dev.you.co:5432/u_card
//    jdbc:postgresql://rds.sg.sit.you.co:5432/u_user
//    jdbc:postgresql://rds.sit.you.co:5432/u_user
    private String baseUri_;
    private String dbName_;
    private String usrname_;
    private String pwd_;
    private Connection conn_ = null;

    public void setDBName(String dbName){
        this.closeConnection();
        this.conn_ = null;
        this.dbName_ = dbName;
        this.usrname_ = dbName.replace("_", "");
        this.pwd_ = dbName.replace("_", "");
    }

    public static DBConnectionHandler getInstance(boolean isDevEnv, Market currentMarket){
        if (DBConnectionHandler.instance == null){
            DBConnectionHandler.instance = new DBConnectionHandler(isDevEnv, currentMarket);
        }
        return DBConnectionHandler.instance;
    }

    private DBConnectionHandler(boolean isDevEnv, Market currentMarket){
        if(isDevEnv){
            this.baseUri_ = "jdbc:postgresql://rds.dev.you.co:5432/";
        }else if(currentMarket.equals(TestAccountData.Market.Singapore)){
            this.baseUri_ = "jdbc:postgresql://rds.sg.sit.you.co:5432/";
        }else{
            this.baseUri_ = "jdbc:postgresql://rds.sit.you.co:5432/";
        }
        this.closeConnection();
    }

    private boolean establishConnection() {
        try {
            if(!this.closeConnection())
                return false;

            String connStr = this.baseUri_ + dbName_;
            this.conn_ = DriverManager.getConnection(connStr, this.usrname_, this.pwd_);
            return true;
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return false;
        }
    }

    private boolean closeConnection(){
        try {
            if(this.conn_ == null){
                return true;
            }
            if (!this.conn_.isClosed()) {
                this.conn_.close();
            }
            return true;
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return false;
        }
    }

    /*javadoc:
    * Returned ResultSet is 1-based indexing Iterative
    * Each Iterative respresenting a row
    * */
    public ResultSet executeQuery(String statement){
        try {
            if (establishConnection()) {
                Statement stmt = this.conn_.createStatement();
                ResultSet rset = stmt.executeQuery(statement);
                closeConnection();
                return rset;
            }else {
                return null;
            }
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return null;
        }
    }
    private int executeProcedure(PreparedStatement stmt){
        try {
            if (establishConnection()) {
                int result = stmt.executeUpdate();
                closeConnection();
                return result;
            }else {
                return -1;
            }
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return -1;
        }
    }

    public int executeProcedure(String statement){
        try {
            PreparedStatement stmt = this.conn_.prepareStatement(statement);
            return this.executeProcedure(stmt);
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return -1;
        }
    }

    public int executeProcedure(String statement, String[] params){
        try {
            PreparedStatement pstmt = this.conn_.prepareStatement(statement);
            for(int i = 0; i < params.length;i++){
                pstmt.setString(i+1, params[i]);
            }
            return this.executeProcedure(pstmt);
        }catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
            return -1;
        }
    }
}
