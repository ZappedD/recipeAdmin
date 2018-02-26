/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import nu.te4.resources.SessionUtils;
import nu.te4.resources.UserRights;

/**
 *
 * @author Geini
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
    
    private String userName, password, result;
    
    public String login() {
        boolean valid = false;
        valid = UserRights.checkCredentials(userName, password);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", userName);
            result = "admin";
        } else {
            result = "Wrong username or password tyr again ";
            //rederect?
        }
        return result;
    }
    
    public void logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        //rederect 
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getResult() {
        return result;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
    
}
