/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import daos.UserDAO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SE140066
 */
public class AuthenFilter implements Filter {

    private final List<String> USER;
    private final List<String> ADMIN;
    private final String LOGIN = "login.jsp";
    private final String AD = "AD";
    private final String US = "US";

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    public AuthenFilter() {
        USER = new ArrayList<>();
        USER.add("login.jsp");
        USER.add("error.jsp");
        USER.add("MainController");
        USER.add("LoginController");
        USER.add("LogoutController");
        USER.add("welcome.jsp");
        USER.add("result.jsp");
        USER.add("quiz.jsp");
        USER.add("createuser.jsp");
        USER.add("AcceptController");
        USER.add("QuizController");
        USER.add("ResultController");
        USER.add("WelcomeController");
        USER.add("UpdateQuestController");
        USER.add("ManageController");
        USER.add("");

        ADMIN = new ArrayList<>();
        ADMIN.add("login.jsp");
        ADMIN.add("error.jsp");
        ADMIN.add("MainController");
        ADMIN.add("LoginController");
        ADMIN.add("LogoutController");
        ADMIN.add("CreateController");
        ADMIN.add("manage.jsp");
        ADMIN.add("history.jsp");
        ADMIN.add("create.jsp");
        ADMIN.add("createsub.jsp");
        ADMIN.add("createuser.jsp");
        ADMIN.add("AddController");
        ADMIN.add("ManageController");
        ADMIN.add("HistoryAdminController");
        ADMIN.add("SearchController");
        ADMIN.add("UpdateQuestController");
        ADMIN.add("CreateSubController");
        ADMIN.add("");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String uri = req.getRequestURI();
            if (uri.contains(".jpg") || uri.contains(".png") || uri.contains(".gif") || uri.contains(".css")) {
                chain.doFilter(request, response);
            } else if (uri.contains("login.jsp") || uri.contains("MainController") || uri.contains("error.jsp")
                    || uri.contains("createuser.jsp") || uri.contains("create.jsp") || uri.contains("createsub.jsp") 
                    || uri.contains("UpdateQuestController") || uri.contains("ManageController")) {
                chain.doFilter(request, response);
                return;
            }
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);
            HttpSession session = req.getSession();
            if (session == null || session.getAttribute("FULLNAME") == null) {
                res.sendRedirect(LOGIN);
            } else {
                String fullName = (String) session.getAttribute("FULLNAME");
                UserDAO dao = new UserDAO();
                String roleID = dao.checkRoleIDbyFullName(fullName);
                if (roleID.equalsIgnoreCase(AD) && ADMIN.contains(resource)) {
                    chain.doFilter(request, response);
                } else if (roleID.equalsIgnoreCase(US) && USER.contains(resource)) {
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect(LOGIN);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
