/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import nu.te4.resources.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import nu.te4.resources.User;
import nu.te4.resources.UserRights;

/**
 *
 * @author Geini
 */
@Named
@RequestScoped
public class UsersBean{
    List<User> users = new ArrayList<>();
    
    public UsersBean() {
        users = UserRights.getUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public boolean removeUser(int id ) {
        boolean result = UserRights.removeUser(id);
        users = UserRights.getUsers();
        return result;
    }
    
    
        
    
}
