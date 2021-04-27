/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import static java.lang.Math.log;
import static java.lang.StrictMath.log;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author SE140066
 */
public class DBUtils {
    public static Connection getCon(){
        Connection conn = null;
        try {
            Context context = (Context) new InitialContext();
            Context end = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) end.lookup("dbCon");
            conn = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            System.out.println(ex);
            System.out.println("error");
        }
        return conn;
    }
}
