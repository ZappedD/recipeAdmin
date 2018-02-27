/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
    
    private String username, password, result;
    
    public String login() {
        boolean valid = false;
        valid = UserRights.checkCredentials(username, password);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", username);
            result = "admin";
        } else {
            result = "Wrong username or password tyr again ";
            //rederect?
        }
        return result;
    }
    
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        //redierect 
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getResult() {
        return result;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResult(String result) {
        this.result = result;
    }
  
}
