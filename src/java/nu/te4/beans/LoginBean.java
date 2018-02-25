/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Jacob
 */
@Stateless
public class LoginBean {

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
                if (result.getString("rights") == "admin") {
                    if (BCrypt.checkpw(password, result.getString("password"))) {
                        return true;
                    } else {
                        //fel lösen
                        return false;
                    }
                }

            } else {
                //user not found 
                System.out.println("User Not found");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return false;

    }
}
