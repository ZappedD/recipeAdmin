/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author Geini
 */
@Stateless
public class UsersBean{
    
    public JsonArray getUsers() throws SQLException{
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareCall(
            "SELECT * FROM users");
            ResultSet result = pstmt.executeQuery();
            JsonArray jsonUsers = getUsersFromSqlData(result);
            connection.close();
            return jsonUsers;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }
    
    private JsonArray getUsersFromSqlData(ResultSet data) throws SQLException{
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        while (data.next()) {
            JsonObject users = Json.createObjectBuilder()
                    .add("userName", data.getString("username"))
                    .add("id", data.getString("id"))
                    .add("rights", data.getString("rights"))
                    .build();
            jsonArrayBuilder.add(users);
        }
        return jsonArrayBuilder.build();
    }
    
    public boolean removeUser(int userId) throws SQLException{
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareCall(
                    "DELETE FROM users WHERE id =?");
            pstmt.setInt(1, userId);
            pstmt.executeQuery();
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }
    
    
}
