/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.json.JsonArray;
/**
 *
 * @author Jacob
 */
@Named
@SessionScoped
public class PrimeBean implements Serializable {
    private String username,password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
      
    private JsonArray users;
         
    @ManagedProperty("nu.te4.beans.UsersBean")
    private UsersBean usersBean;
     
    @PostConstruct
    public void init() {
        try {
            users = usersBean.getUsers();
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }
 
    public JsonArray getCars() {
        return users;
    }
 
 
   
}
