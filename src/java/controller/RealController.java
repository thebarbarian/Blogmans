package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Comment;
import model.Posting;
import service.WebLogService;

/**
 *
 * @author peterk
 */
@WebServlet (
        name="RealController",
        loadOnStartup=1,
        urlPatterns ={
          "/postMessage",
          "/GoWebLog",
          "/gotoWebLog"         
        })        


public class RealController extends HttpServlet {
   
    //WebLogService wls = new WebLogService();
    private WebLogService wls;
    
    @Override
    public void init()
    {
        wls = new WebLogService();        
    }
       @Override
     public void doGet (HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
      {
      String userpath = request.getServletPath();     
      String url;
      url = "";
       Long postNr;
      switch (userpath) {
        case "/goWeblog":
             request.setAttribute("allPostings", wls.getPostings());
            url="WebLog.jsp";
        break;
        case "/goWeblogAdm":
            url="WeblogAdm.jsp";
            break;
        case "/Response":
            url="Response.jsp";
           // postNr = Long.decode(request.getParameter("postId"));
            postNr = Long.parseLong(request.getParameter("postId"));
            Posting pfc = wls.getPost(postNr);  
            List<Comment> l = wls.getComments(postNr);         
            request.setAttribute("allComments",l);
            request.setAttribute("PostForComment", pfc);
            break;
      }
      request.getRequestDispatcher(url).forward(request, response);
      }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userpath = request.getServletPath();                    
        String url;     
        url="";
        //String AdminMode="Basic";
        String AdminMode="";
       
         switch (userpath) {
             
             case "/deletePost":
                Long pid = Long.decode(request.getParameter("postId"));
                Posting q = wls.getPost(pid);
                wls.deletePost(q);        
                request.getSession().setAttribute("allPostings", wls.getPostings());
                response.sendRedirect("/BlogMans/goWeblogAdm");
                return;  
             
            case "/postMessage": 
                // Als post Id is n dan gaat het om een nieuwe post:                
                if( request.getSession().getAttribute("postId") == null){ 
                    Posting p = new Posting(request.getParameter("postTitle").toString(),
                            request.getParameter("postContent").toString());
                    wls.addPosting(p);
                    Long laatstePostId = wls.getLastPostId();
                    request.setAttribute("lastPostId", laatstePostId);  
                    request.getSession().setAttribute("allPostings", wls.getPostings());
                    response.sendRedirect("/BlogMans/goWeblogAdm");
                    return;                           
                }                
                    if( request.getSession().getAttribute("postId") != null){ 
                        Posting p = new Posting(
                                  //  Long.parseLong(request.getParameter("postId")),
                                    Long.parseLong( request.getSession().getAttribute("postId").toString() ),
                                    request.getParameter("postTitle").toString(),
                                    request.getParameter("postContent").toString());
                        wls.replacePost(p);
                        request.getSession().setAttribute("allPostings", wls.getPostings());
                        response.sendRedirect("/BlogMans/goWeblogAdm");                    
                        request.getSession().removeAttribute("postId");
                        return;                     
                }
                
                break; 
            case "/goWeblog":
                url="WebLog.jsp";
                request.setAttribute("allPostings", wls.getPostings());    
                request.getSession().setAttribute("allPostings", wls.getPostings());
                String lijstje = request.getAttribute("allPostings").toString();
                RequestDispatcher view = request.getRequestDispatcher(url);
                view.forward(request, response);
                break;   
            case "/newComment":
                Long id = Long.decode(request.getParameter("postId"));                
                Comment c;
                c = new Comment(id,request.getParameter("newComment"));
                wls.addComment(c);                
                request.setAttribute("PostForComment",wls.getPost(id));
                request.setAttribute("allComments",wls.getComments(id));
                //url="Response.jsp";
                url = ""; // een nieuwe post mag niet leiden tot het laden van een nieuwe pagina!
                break;
            case "/setAdminMode":
               // if(request.getParameter("ModeKnop")!=null){
                switch (request.getParameter("ModeKnop")) {
                    case "BasicMode":
                        AdminMode = "Basic";
                        request.setAttribute("AdminMode", AdminMode);
                        request.getSession().setAttribute("AdminMode", AdminMode);
                        request.getSession().setAttribute("allPostings", wls.getPostings());
                        response.sendRedirect("/BlogMans/goWeblogAdm");
                        return;
                        //url="WeblogAdm.jsp";
                        //break;
                    case "AdvancedMode":
                        AdminMode = "Advanced";
                        request.setAttribute("AdminMode", AdminMode);
                        request.getSession().setAttribute("AdminMode", AdminMode);
                        request.getSession().setAttribute("allPostings", wls.getPostings());
                        response.sendRedirect("/BlogMans/goWeblogAdm");
                        return;                       
                } break;         
                 
                case "/goWeblogAdm":          
                    response.sendRedirect("/BlogMans/goWeblogAdm");
                    return;
                    
            case "/setEditPost":
                if(     request.getParameter("postId")!= null &&
                        request.getParameter("postTitle")!=null && 
                        request.getParameter("postContent")!=null){
                        request.setAttribute("postId", request.getParameter("postId"));
                        request.setAttribute("postTitle", request.getParameter("postTitle"));
                        request.setAttribute("postContent", request.getParameter("postContent")); 
                        request.getSession().setAttribute("postId", request.getParameter("postId"));                   
                        response.sendRedirect("/BlogMans/goWeblogAdm");
                        return;                    
                }                        
         }        
         
    request.setAttribute("allPostings", wls.getPostings());    
    String lijstje = request.getAttribute("allPostings").toString();
    RequestDispatcher view = request.getRequestDispatcher(url);
    view.forward(request, response);   
    }
}
