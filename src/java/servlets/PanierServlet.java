/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import dao.ArticleDao;
import entities.Article;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import panier.Panier;

/**
 *
 * @author Walid
 */
public class PanierServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(request.getSession().getAttribute("panier") == null)
            request.getSession().setAttribute("panier", new Panier());
        
        Panier p = (Panier)request.getSession().getAttribute("panier");
            
        ArticleDao dao = new ArticleDao();
        int id = Integer.parseInt(request.getParameter("id"));
        Article a = dao.findByID(id);
        
        if(request.getParameter("op") == null || a == null)
        {
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&failed");
            return ;
        }
        
        
        if(request.getParameter("op").equals("add"))
        {
            p.addArticle(a);
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&succes");
        }
        
        if(request.getParameter("op").equals("sous"))
        {
            p.sousArticle(a);
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&succes");
        }
        
        if(request.getParameter("op").equals("rem"))
        {
            p.removeArticle(a);
            response.sendRedirect(request.getParameter("sender") + "?id="+id+"&succes");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
