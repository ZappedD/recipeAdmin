/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.resources;

/**
 *
 * @author Jacob
 */
public class User {
    private String username, password, rights;
    private int id;
    
    public User(String username, String password, String rigths, int id) {
        this.username = username;
        this.password = password;
        this.rights = rigths;
        this.id = id;
    }
    
    public String getUsername(){
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRights() {
        return rights;
    }

    public int getId() {
        return id;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
