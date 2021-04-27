/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

/**
 *
 * @author SE140066
 */
public class UserDAO {

    public String checkLogin(String userID, String password) {
        String fullName = "";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT userName FROM tblUsers WHERE userID = ? AND password = ? AND status = 'True'";
            stm = conn.prepareStatement(sql);
            stm.setString(1, userID);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                fullName = rs.getString(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return fullName;
    }

    public boolean checkID(String userID) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "Select userID, userName, roleID FROM tblUsers WHERE userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                result = rs.next();
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return result;
    }

    public String checkRole(String userID) throws SQLException {
        String result = "";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "Select roleID FROM tblUsers WHERE userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString(1);
                }
                
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return result;
    }
    
    
    public String checkRoleIDbyFullName(String fullName) throws SQLException {
        String result = "";
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            if (conn != null) {
                String sql = "Select roleID FROM tblUsers WHERE userName = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, fullName);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString(1);
                }
                
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return result;
    }
    
    public void create(UserDTO dto) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblUsers (userID, userName, password, roleID, phoneNumber, address, email, status) VALUES(?,?,?,?,?,?,?,'1')";
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getUserID());
            stm.setString(2, dto.getFullName());
            stm.setString(3, dto.getPassword());
            stm.setString(4, dto.getRoleID());
            stm.setString(5, dto.getPhone());
            stm.setString(6, dto.getAddress());
            stm.setString(7, dto.getEmail());
            stm.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
