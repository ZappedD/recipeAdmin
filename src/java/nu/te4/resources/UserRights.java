/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.ejb.Stateless;
import nu.te4.beans.BCrypt;
import nu.te4.beans.ConnectionFactory;

/**
 *
 * @author Jacob
1 */
@Stateless
public class UserRights {

    public boolean checkCredentials(String basic_auth) {
        basic_auth = basic_auth.substring(basic_auth.indexOf(" ") + 1);
        System.out.println("The passsword ch " + basic_auth);
        byte[] decode = Base64.getDecoder().decode(basic_auth);
        String credentials = new String(decode);

        String username = credentials.substring(0, credentials.indexOf(":"));
        String password = credentials.substring(credentials.indexOf(":") + 1);

        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE username=?");
            pstmt.setString(1, password);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                boolean confirmed = BCrypt.checkpw(password, result.getString("password"));
                if (confirmed && result.getString("rights") == "admin") {
                    connection.close();
                    return true;
                } else {
                    //not admin or wrong password
                    connection.close();
                    return false;
                }
                
            } else {
                //user not found 
                connection.close();
                System.out.println("User Not found");
                return false;
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
            ptsmt.executeQuery();
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Eroro : " + e.getMessage());
            return false;
        }
    }
    
}
