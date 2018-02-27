/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.resources;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nu.te4.beans.LoginBean;

/**
 *
 * @author Geini
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"*.xhtml"})
public class LoginFilter {

    
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }

    public void destroy() {
        // Nothing to do here!
    }
    
     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         HttpServletRequest reqt = (HttpServletRequest) request;
         HttpServletResponse resp = (HttpServletResponse) response;
         HttpSession sess = reqt.getSession(false);
         
         String reqURI = reqt.getRequestURI();
         
        if (reqURI.indexOf("/index.xhtml") >=0
                || (sess != null && sess.getAttribute("user") != null)
                || reqURI.indexOf("/public/") >= 0
                || reqURI.contains("javax.faces.resource") ) {
              chain.doFilter(request, response);
        } else {
            resp.sendRedirect(reqt.getContextPath() + "/faces/index.xhtml");
        }
             
    }
    
}
