/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Comment;
import model.Posting;

public interface PostingDao {

    void create(Posting p, ArrayList<Comment> l);

    List<Posting> findAll();

    Posting find(Long id);
    
    public Long getLastPostId();
    
    void createComment (Comment c);
    
    List<Comment> findAllComments(Long id);  
    
    public void replacePost(Posting p);
    
    public void deletePost(Posting p);
    
    // nog geen behoefte aan het zoeken van een specifieke comment.
}
