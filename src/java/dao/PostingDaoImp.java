/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Comment;
import model.Posting;

/**
 *
 * @author Administrator
 */
public final class PostingDaoImp implements PostingDao {

    private HashMap<Long, Posting> postings;
    private HashMap<Long, List<Comment>> comments;
    private static Long nextId;

    public PostingDaoImp() {
        initWeblog();
    }

    public void initWeblog() {

        postings = new HashMap<>();
        comments = new HashMap<>();

        postings.put(1L, new Posting(1L, "How to train your mother in law", "No comments."));
        postings.put(2L, new Posting(2L, "Unforgiven", "Unforgiven, the third best movie ever made. Story about an outlaw by the name of William Munny who is the baddest of baddasses."));
        postings.put(3L, new Posting(3L, "Once upon a time in the west", "Best movie ever made."));

        List commL1 = new ArrayList<>();
        commL1.add(new Comment(1L, "Good movie with hilarious humor."));
        commL1.add(new Comment(1L, "Coming soon to a cinema near you!"));
        comments.put(1L, commL1);

        List commL2 = new ArrayList<>();
        commL2.add(new Comment(2L, "Enjoyed this one"));
        commL2.add(new Comment(2L, "Awesome Clint Eastwood movie. Must see!"));
        comments.put(2L, commL2);

        List commL3 = new ArrayList<>();
        commL3.add(new Comment(3L, "Good to see Charles Bronson was young once."));
        comments.put(3L, commL3);

        nextId = 4L;

    }

    @Override
    public void create(Posting p, ArrayList<Comment> l) {
        if (p == null) {
            throw new IllegalArgumentException("Posting is null");
        }
        p.setId(nextId);
        postings.put(nextId++, p);        
    }

    @Override
    public Long getLastPostId() {
        return (nextId - 1);
    }

    @Override
    public List<Posting> findAll() {
        return new ArrayList(postings.values());
    }

    @Override
    public Posting find(Long id) {
        if (!postings.containsKey(id)) {
            throw new IllegalArgumentException("Id no found" + id);
        }
        return postings.get(id);
    }

    @Override
    public void createComment(Comment c) {
        if (c == null) {
            throw new IllegalArgumentException("Posting is null");
        }
        // First we need the post that the comment is meant for.
        // The LONG id from the comment will be considered the Post id
        // since we are not planning on using separate stuff for commenting.

        // We need to get the right posting from the comment id.
        // Next step is to get the list of comments and add an element to that list.
        // then we add the list to the hashmap again under the same id.
        if (comments.containsKey(c.getId())) {
            List<Comment> l = comments.get(c.getId());
            l.add(c);
            comments.put(c.getId(), l);
        } else {
            List<Comment> l = new ArrayList<>();
            l.add(c);
            comments.put(c.getId(), l);
        }
    }

    @Override
    public List<Comment> findAllComments(Long id) {
        return comments.get(id);
    }
    
    @Override
    public void replacePost(Posting p){
        postings.put(p.getId(), p); // dit mag gelukkig een keer wel.
    }
    
    @Override
    public void deletePost(Posting p){
        postings.remove(p.getId());
        // comments moeten ook weg ivm reusen van key!
        comments.remove(p.getId());
    }
    
}
