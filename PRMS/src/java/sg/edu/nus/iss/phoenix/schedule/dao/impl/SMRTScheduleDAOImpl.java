/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAOImpl;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.SMRTScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.SMRTRadioProgramSlot;

/**
 *
 * @author Samrat
 */
public class SMRTScheduleDAOImpl implements SMRTScheduleDAO{
    
    /************************************/
    // Instance Variables
    /************************************/
    Connection connection;
    
    /************************************/
    // Constructor
    /************************************/
    public SMRTScheduleDAOImpl() {
        
    }
    
    /************************************/
    // Interface Method Implementation
    /************************************/
    
    @Override
    public void create(SMRTRadioProgramSlot valueObject) throws SQLException {
        String sql = "";
        PreparedStatement stmt = null;
        openConnection();
        try {
            sql = "INSERT INTO `radio-program-slot` (`startDateTime`, `radioProgram`, `presenter`,`producer`) VALUES (?,?,?,?); ";
            stmt = connection.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.valueOf(valueObject.getStartDate()));
            stmt.setString(2, valueObject.getRadioProgram().getName());
            stmt.setString(3, valueObject.getPresenter().getId());
            stmt.setString(4, valueObject.getProducer().getId());
            int rowcount = stmt.executeUpdate();
            if (rowcount != 1) {
                throw new SQLException("PrimaryKey Error when insert DB!");
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException("Error while adding radio program slot.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }
    
    public List<ProgramSlot> load(SMRTRadioProgramSlot valueObject)
            throws NotFoundException, SQLException {
        return null;
    }
    
    public List<SMRTRadioProgramSlot> loadAll() throws SQLException {
        String sql = "SELECT * FROM `radio-program-slot`; ";
        PreparedStatement stmt = null;
        openConnection();
        try {
            stmt = connection.prepareStatement(sql);
            return listQuery(stmt);
        } catch(Exception e) {
            e.printStackTrace();
            throw new SQLException("Something went wrong while retrieving Program Slots.");
        }finally {
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }
    }
    
    public void save(SMRTRadioProgramSlot valueObject) throws NotFoundException,SQLException {
        
    }
    
    public void delete(SMRTRadioProgramSlot valueObject)
            throws SQLException {
        
    }

    public void deleteAll() throws SQLException {
        
    }
    
    /************************************/
    // Private Method Implementation
    /************************************/
    private void openConnection() {
        try {
            Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection(DBConstants.dbUrl,
                    DBConstants.dbUserName, DBConstants.dbPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    protected List<SMRTRadioProgramSlot> listQuery(PreparedStatement stmt) throws SQLException, NotFoundException {

        ArrayList<SMRTRadioProgramSlot> searchResults = new ArrayList<SMRTRadioProgramSlot>();
        
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        ProgramDAOImpl programDAOImpl = new ProgramDAOImpl();
        
        ResultSet result = null;
        openConnection();
        try {
            result = stmt.executeQuery();

            while (result.next()) {
                SMRTRadioProgramSlot temp = new SMRTRadioProgramSlot();
                temp.setId(result.getInt("id"));
                temp.setStartDateTime(result.getTimestamp("startDateTime").toLocalDateTime());
                
                RadioProgram radioProgram = new RadioProgram(result.getString("radioProgram"));
                programDAOImpl.load(radioProgram);
                temp.setRadioProgram(radioProgram);
                
                temp.setPresenter(userDaoImpl.getObject(result.getString("presenter")));
                temp.setProducer(userDaoImpl.getObject(result.getString("producer")));

                searchResults.add(temp);
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            closeConnection();
        }

        return searchResults;
    }
}