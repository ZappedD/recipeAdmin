/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jacob
1 */

public class UserRights {

    public static boolean checkCredentials(String username, String password) {
        try {
            boolean outcome = false;
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE username=?");
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            System.out.println(FacesContext.getCurrentInstance().getViewRoot().getViewId());

            if (result.next()) {
                
                String rights = result.getString("rights");
                String hasedPassword = result.getString("password");
                
                System.out.println("Password check");
                outcome = BCrypt.checkpw(password, hasedPassword);
                System.out.println(outcome);
                if (outcome && rights.equals("admin")) {
                    connection.close();
                    return outcome;
                } else {
                    //not admin or wrong password
                    System.out.println("Not admin");
                    connection.close();
                }
                return outcome;
            } else {
                //user not found 
                connection.close();
                System.out.println("User Not found");
                return outcome;
            }
                    
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return false;
    }
    
    public static boolean removeUser(int id) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ptsmt = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?");
            ptsmt.setInt(1, id);
            ptsmt.executeUpdate();
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Eroro : " + e.getMessage());
            return false;
        }
    }
    
    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM users");
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {                
                users.add(new User(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("rights"),
                        result.getInt("id")
                ));
            }
            connection.close();
            return users;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }
    
    public static boolean setRights(String username) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE users SET rights = ? WHERE username = ?");
            pstmt.setString(1, "admin");
            pstmt.setString(2, username);
            pstmt.executeQuery();
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }
}
