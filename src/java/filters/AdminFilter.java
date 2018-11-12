/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import database.NotesDBException;
import database.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author 743953
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest myrequest = (HttpServletRequest) request;
        HttpServletResponse myresponse = (HttpServletResponse) response;

        HttpSession session = myrequest.getSession();
        UserDB userdb = new UserDB();

        String username = (String) session.getAttribute("username");
        try {
            User user = userdb.getUser(username);
            
            if(user.getRole().getRoleid() == 1){
                //user is admin
                //allow to continue to servlet
                chain.doFilter(request, response);
            } else {
                //user is not admin
                //send them to home page
                myresponse.sendRedirect("home");
            }
            
        } catch (NotesDBException ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
