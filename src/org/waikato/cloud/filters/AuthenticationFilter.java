package org.waikato.cloud.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class AuthenticationFilter implements Filter 
{
 
    private ServletContext context;
    
    private ArrayList<String> urlList;
     
    public void init(FilterConfig config) throws ServletException 
    {
        this.context = config.getServletContext();
        this.context.log("AuthenticationFilter initialized");
        
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
 
        urlList = new ArrayList<String>();        
        while (token.hasMoreTokens()) 
        {
            urlList.add(token.nextToken());
        }
        this.context.log("Allowed URLs = " + urlList);
    }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String url = req.getServletPath();        
        boolean allowedRequest = false;
        if(urlList.contains(url))
        {
        	allowedRequest = true;
        }
        else if( url.endsWith(".html") || url.endsWith(".css") || url.endsWith(".jpg") || url.endsWith(".js") )
        {
        	allowedRequest = true;
        }
        if (!allowedRequest)
        {
        	//this.context.log("URL = " + url);
            HttpSession session = req.getSession(false);            
            if (null == session || session.getAttribute("user") == null )
            {
                res.sendRedirect("/login.jsp");
                return;
            }
        }
        
        chain.doFilter(request, response);        
    }
 
    public void destroy() 
    {
    } 
}
