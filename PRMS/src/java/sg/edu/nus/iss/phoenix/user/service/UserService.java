/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author Samrat
 */
public class UserService {

    /**
     * ************************************************
     */
    // Instance Variables
    /**
     * ************************************************
     */
    DAOFactoryImpl factory;
    UserDao userDao;

    /**
     * ************************************************
     */
    // Constructors
    /**
     * ************************************************
     */
    public UserService() {
        super();
        // TODO Auto-generated constructor stub
        factory = new DAOFactoryImpl();
        userDao = factory.getUserDAO();
    }

    /**
     * ************************************************
     */
    // Public Methods
    /**
     * ************************************************
     */
    /**
     * Method to get all user present in the database.
     *
     * @return List containing all users present in database.
     */
    public List<User> reviewSelectUser() {
        List<User> data = null;
        try {
            data = userDao.loadAll();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public void processCreate(User user) {
        try {
            userDao.create(user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void processModify(User user) {

        try {
            userDao.save(user);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void processDelete(String id) {

        try {
            User user = userDao.getObject(id);
            userDao.delete(user);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}