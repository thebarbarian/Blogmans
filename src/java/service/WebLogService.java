package service;

import dao.PostingDao;
import dao.PostingDaoImp;
import java.util.ArrayList;
import java.util.List;
import model.Comment;
import model.Posting;

public class WebLogService {

    private PostingDao postingDao;

    public WebLogService() {
        postingDao = new PostingDaoImp();
    }

    public void addPosting(Posting p) {
        
        ArrayList<Comment> l = new ArrayList<Comment>();
        postingDao.create(p,l);        
    }

    public List<Posting> getPostings() {
        return postingDao.findAll();
    }
    
    public Posting getPost(Long id)
    {
        return postingDao.find(id);
    }
    
    public void addComment(Comment c)
    {
        postingDao.createComment(c);
    }
    
    public Long getLastPostId()
    {
        return postingDao.getLastPostId();
    }
    
    public List<Comment> getComments(Long id)
    {
        return postingDao.findAllComments(id);
    }
    
    public void replacePost(Posting p){
        postingDao.replacePost(p);
    }
    
    public void deletePost(Posting p){
        postingDao.deletePost(p);
    }
    
        
    
}
