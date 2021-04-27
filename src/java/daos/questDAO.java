/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.AnswerDTO;
import dtos.HistoryDTO;
import dtos.MultichoiceDTO;
import dtos.QuizDTO;
import dtos.QuestionDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

/**
 *
 * @author SE140066
 */
public class questDAO {

    public List<String> getListSubject() {
        List<String> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT DISTINCT subjectID FROM tblSubject";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String subject = rs.getString(1);

                if (list == null) {
                    list = new ArrayList<String>();
                }
                list.add(subject);
            }
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<AnswerDTO> getListAnswer(int questionID) {
        List<AnswerDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT answerA, answerB, answerC, answerD FROM tblQuestion WHERE questionID = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, questionID);
            rs = stm.executeQuery();
            if (rs.next()) {
                String answerA = rs.getString(1);
                String answerB = rs.getString(2);
                String answerC = rs.getString(3);
                String answerD = rs.getString(4);

                if (list == null) {
                    list = new ArrayList<AnswerDTO>();
                }
                list.add(new AnswerDTO(answerA, answerB, answerC, answerD));
            }
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    /*-------------Count Page--------------*/
    public int getNumberPage() {
        int total = 0;
        int countPage = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT COUNT(questionID) FROM tblQuestion";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    total = rs.getInt(1); //20
                    int size = 10;
                    countPage = total / size; // 20 : 5

                    if (total % size != 0) {
                        countPage++;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return countPage;
    }

    public ArrayList<QuestionDTO> getPaging(int index, String search, String subject) {
        ArrayList<QuestionDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT questionID, questionContent, answerA, answerB, answerC, answerD, answerCorrect, createDate, subjectID, status "
                        + "from tblQuestion "
                        + "WHERE questionContent LIKE ? AND subjectID LIKE ? "
                        + "order by questionID "
                        + "offset ? rows "
                        + "fetch first 10 rows only";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + subject + "%");
                stm.setInt(3, (index - 1) * 10);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt(1);
                    String questionName = rs.getString(2);
                    String answerA = rs.getString(3);
                    String answerB = rs.getString(4);
                    String answerC = rs.getString(5);
                    String answerD = rs.getString(6);
                    String answerCorrect = rs.getString(7);
                    Date createDate = rs.getDate(8);
                    subject = rs.getString(9);
                    boolean status = rs.getBoolean(10);
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, answerCorrect, createDate, subject, status));

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public ArrayList<QuestionDTO> getPagingSearch(int index, String search, String subject, boolean status) {
        ArrayList<QuestionDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT questionID, questionContent, answerA, answerB, answerC, answerD, answerCorrect, createDate, subjectID, status "
                        + "from tblQuestion "
                        + "WHERE questionContent LIKE ? AND subjectID LIKE ? AND status = ? "
                        + "order by questionID "
                        + "offset ? rows "
                        + "fetch first 10 rows only";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + subject + "%");
                stm.setBoolean(3, status);
                stm.setInt(4, (index - 1) * 10);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt(1);
                    String questionName = rs.getString(2);
                    String answerA = rs.getString(3);
                    String answerB = rs.getString(4);
                    String answerC = rs.getString(5);
                    String answerD = rs.getString(6);
                    String answerCorrect = rs.getString(7);
                    Date createDate = rs.getDate(8);
                    subject = rs.getString(9);
                    status = rs.getBoolean(10);
                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, answerCorrect, createDate, subject, status));

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    /*--------------Admin Function--------------*/
    public List<QuestionDTO> getListQuest() {
        List<QuestionDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getCon();
            String sql = "SELECT questionID, questionContent, answerA, answerB, answerC, answerD, answerCorrect, createDate, subjectID, status "
                    + "FROM tblQuestion ORDER BY questionID ASC;";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int questionID = rs.getInt(1);
                String questionName = rs.getString(2);
                String answerA = rs.getString(3);
                String answerB = rs.getString(4);
                String answerC = rs.getString(5);
                String answerD = rs.getString(6);
                String answerCorrect = rs.getString(7);
                Date createDate = rs.getDate(8);
                String subject = rs.getString(9);
                boolean status = rs.getBoolean(10);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, answerCorrect, createDate, subject, status));

            }
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public static void updateQuest(QuestionDTO dto) throws SQLException, ParseException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblQuestion SET questionContent = ?, answerA = ?, answerB = ?, answerC = ?, answerD = ?, "
                    + "answerCorrect = ?, createDate = ?, subjectID = ?, status = ?"
                    + " WHERE questionID = ?;";
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getQuestionName());
            stm.setString(2, dto.getAnswerA());
            stm.setString(3, dto.getAnswerB());
            stm.setString(4, dto.getAnswerC());
            stm.setString(5, dto.getAnswerD());
            stm.setString(6, dto.getAnswerCorrect());
            stm.setDate(7, (Date) dto.getCreateDate());
            stm.setString(8, dto.getSubjectID());
            stm.setBoolean(9, dto.isStatus());
            stm.setInt(10, dto.getQuestionID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteQuest(int questionID) throws SQLException, ParseException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblQuestion SET status = '0'"
                    + " WHERE questionID = ?;";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, questionID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /*------------Create Item---------------*/
    public void createItem(QuestionDTO dto) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblQuestion (questionContent, answerA, answerB, answerC, answerD, answerCorrect, "
                    + "createDate, subjectID, status) "
                    + "OUTPUT inserted.questionID "
                    + "VALUES(?,?,?,?,?,?,?,?,'1')";
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getQuestionName());
            stm.setString(2, dto.getAnswerA());
            stm.setString(3, dto.getAnswerB());
            stm.setString(4, dto.getAnswerC());
            stm.setString(5, dto.getAnswerD());
            stm.setString(6, dto.getAnswerCorrect());
            stm.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
            stm.setString(8, dto.getSubjectID());
            stm.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void createScore(String userID) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblScore (date, totalScore, userID) "
                    + "OUTPUT inserted.scoreID "
                    + "VALUES(?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stm.setFloat(2, 0);
            stm.setString(3, userID);
            stm.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int getScoreID(String userID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int score = 0;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT scoreID FROM tblScore WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    score = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return score;
    }

    public String createQuiz(int scoreID, String subjectID, int num) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        Random generator = new Random();
        int randNum = generator.nextInt();

        try {
            conn = DBUtils.getCon();
            String sql = "SELECT TOP " + num + " questionID FROM tblQuestion WHERE subjectID = ? AND status = '1' "
                    + "ORDER BY NEWID()";
            stm = conn.prepareStatement(sql);
            stm.setString(1, subjectID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String sqlQuiz = "INSERT INTO tblQuiz (quizName, questionID, scoreID, answer, score) "
                        + "OUTPUT inserted.quizID"
                        + " VALUES (?,?,?,?,?)";
                stm = conn.prepareStatement(sqlQuiz);
                stm.setString(1, subjectID + "_" + randNum);
                stm.setInt(2, rs.getInt(1));
                stm.setInt(3, scoreID);
                stm.setString(4, "Empty!");
                stm.setFloat(5, 0);
                stm.executeQuery();
            }

        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return subjectID + "_" + randNum;
    }

    public int getNumberQuestion(String subjectID) {
        int total = 0;
        int countPage = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT COUNT(quizID) FROM tblQuiz INNER JOIN tblQuestion ON tblQuiz.questionID = tblQuestion.questionID "
                        + "WHERE tblQuiz.quizName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    total = rs.getInt(1); //20
                    int size = 1;
                    countPage = total / size; // 20 : 5

                    if (total % size != 0) {
                        countPage++;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return countPage;
    }

    public ArrayList<QuestionDTO> getPagingQuiz(int index, String subject) {
        ArrayList<QuestionDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT tblQuiz.questionID, questionContent, answerA, answerB, answerC, answerD, answerCorrect "
                        + "from tblQuestion INNER JOIN tblQuiz ON tblQuestion.questionID = tblQuiz.questionID "
                        + "WHERE tblQuiz.quizName = '" + subject
                        + "' AND status = '1' order by questionID "
                        + "offset ? rows "
                        + "fetch first 1 rows only";
                stm = con.prepareStatement(sql);
                stm.setInt(1, (index - 1) * 1);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int questionID = rs.getInt(1);
                    String questionName = rs.getString(2);
                    String answerA = rs.getString(3);
                    String answerB = rs.getString(4);
                    String answerC = rs.getString(5);
                    String answerD = rs.getString(6);
                    String answerCorrect = rs.getString(7);

                    if (list == null) {
                        list = new ArrayList();
                    }
                    list.add(new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, answerCorrect, null, subject, true));

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public void getAnswer(String answer, int questionID) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblQuiz SET answer = ?"
                    + " WHERE questionID = ?;";
            stm = conn.prepareStatement(sql);
            stm.setString(1, answer);
            stm.setInt(2, questionID);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public List<Integer> getQuestionID(String subjectID) {
        List<Integer> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int questionID = 0;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT questionID FROM tblQuiz WHERE quizName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    questionID = rs.getInt(1);

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(questionID);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public String getAnswerQuiz(int questionID, String subjectID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String answer = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT answer FROM tblQuiz WHERE quizName = ? AND questionID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                stm.setInt(2, questionID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    answer = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return answer;
    }

    public String getQuestion(int questionID, String subjectID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String question = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT tblQuestion.questionContent FROM tblQuestion INNER JOIN tblQuiz ON tblQuestion.questionID = tblQuiz.questionID \n"
                        + "WHERE quizName = ? AND tblQuestion.questionID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                stm.setInt(2, questionID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    question = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return question;
    }

    public String getAnswerQuest(int questionID, String subjectID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String answer = null;

        String[] splits = subjectID.split("_");
        for (String item : splits) {
            subjectID = item;
            break;
        }

        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT answerCorrect FROM tblQuestion WHERE subjectID = ? AND questionID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                stm.setInt(2, questionID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    answer = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return answer;
    }

    public void getScore(String subjectID, int questionID, int numQuest) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblQuiz SET score = ?"
                    + " WHERE questionID = ? AND quizName = ?";
            stm = conn.prepareStatement(sql);
            float score = ((float)10) / numQuest;
            stm.setFloat(1, score);
            stm.setInt(2, questionID);
            stm.setString(3, subjectID);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public float setTotalScore(String subjectID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float total = 0;
        float score;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT score FROM tblQuiz WHERE quizName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    score = rs.getFloat(1);
                    total += score;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }

    public void getTotalScore(int scoreID, float totalScore) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "UPDATE tblScore SET totalScore = ?"
                    + " WHERE scoreID = ?";
            stm = conn.prepareStatement(sql);
            stm.setFloat(1, totalScore);
            stm.setInt(2, scoreID);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public List<QuizDTO> getQA(String subjectID) {
        List<QuizDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String answer = null;
        int questionID;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT questionID, answer FROM tblQuiz WHERE quizName = ? ORDER BY questionID";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    questionID = rs.getInt(1);
                    answer = rs.getString(2);

                    QuizDTO dto = new QuizDTO("", answer);

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public void createHistory(String userID, String content, int questionID) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblHistory (userID, [content], createDate, questionID) "
                    + "OUTPUT inserted.historyID "
                    + "VALUES(?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, userID);
            stm.setString(2, content);
            stm.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stm.setInt(4, questionID);
            stm.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int getQuestionIDbyContent(String questionContent) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int questionID = 0;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT questionID FROM tblQuestion WHERE questionContent = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, questionContent);
                rs = stm.executeQuery();
                if (rs.next()) {
                    questionID = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return questionID;
    }

    public List<HistoryDTO> getHistory() {
        List<HistoryDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String answer = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT historyID, userID, content, createDate, questionID FROM tblHistory";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int historyID = rs.getInt(1);
                    String userID = rs.getString(2);
                    String content = rs.getString(3);
                    Date createDate = rs.getDate(4);
                    int questionID = rs.getInt(5);

                    HistoryDTO dto = new HistoryDTO(historyID, userID, content, createDate, questionID);

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public void createSubject(String sucjectID, String subjectName) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getCon();
            String sql = "INSERT INTO tblSubject(subjectID, subjectName) "
                    + "VALUES(?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, sucjectID);
            stm.setString(2, subjectName);
            stm.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public List<String> getSubjectID() {
        List<String> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String subjectID = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT subjectID FROM tblSubject";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    subjectID = rs.getString(1);

                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(subjectID);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public int getNumberQuest(String subjectID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int total = 0;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT COUNT(quizID) FROM tblQuiz WHERE quizName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return total;
    }

    public List<MultichoiceDTO> getQuestionAnswer(String subjectID, String questionContent) {
        List<MultichoiceDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String question = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT tblQuestion.questionContent, tblQuestion.answerA, tblQuestion.answerB, tblQuestion.answerC, tblQuestion.answerD, tblQuestion.answerCorrect, tblQuiz.answer "
                        + "FROM tblQuestion INNER JOIN tblQuiz ON tblQuestion.questionID = tblQuiz.questionID "
                        + "WHERE quizName = ? AND tblQuestion.questionContent LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                stm.setString(2, "%"+ questionContent +"%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    questionContent = rs.getString(1);
                    String answerA = rs.getString(2);
                    String answerB = rs.getString(3);
                    String answerC = rs.getString(4);
                    String answerD = rs.getString(5);
                    String answerCorrect = rs.getString(6);
                    String answer = rs.getString(7);
                    
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new MultichoiceDTO(questionContent, answerA, answerB, answerC, answerD, answerCorrect, answer));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }
    
    public int getNumberQuestofSubject(String subjectID) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT COUNT(questionID) FROM tblQuestion WHERE subjectID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return count;
    }
    
    /*-------------get time decode--------------*/
    public int getTime(String subjectID) {
        int time = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT limitTime FROM tblSubject WHERE subjectID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    time = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return time;
    }
    
    /*-------------get num of quest decode--------------*/
    public int getNumOfQues(String subjectID) {
        int num = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getCon();
            if (con != null) {
                String sql = "SELECT numOfQues FROM tblSubject WHERE subjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, subjectID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(questDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ps != null) {
                try {
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(questDAO.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return num;
    }
}
