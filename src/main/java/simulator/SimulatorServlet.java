package simulator;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SimulatorServlet extends HttpServlet {

    private Simulator simulator;

    public void init() throws ServletException
    {
        this.simulator = new Simulator();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                      throws ServletException, IOException
    {


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
                       throws ServletException, IOException
    {


    }

    public void destroy()
    {

    }
}





