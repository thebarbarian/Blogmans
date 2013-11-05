/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var xhr; 
var Comment;
function addComment(postId){         
            xhr = new XMLHttpRequest();                        
            xhr.open("POST","newComment",true);                        
            xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");                       
            Comment = document.forms['newCommentsForm'].elements['newComment'].value;
            xhr.send("postId="+postId+"&newComment="+Comment); // stuur post vars naar de server voor storage
            xhr.onreadystatechange=tryComment(Comment);
}

function addPosting(){        
            xhr = new XMLHttpRequest();                        
            xhr.open("POST","postMessage",true);                        
            xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");                       
            titel = document.forms["PostingWindow"]["postTitle"].value;
            content = document.forms["PostingWindow"]["postContent"].value;
            xhr.send("&postTitle="+titel+"&postContent="+content);
            xhr.onreadystatechange=tryComment(Comment);
}

function tryComment(Commentaar){
   
                PutCommentInDiv(Commentaar);
            }
function replacePost(postId){
            xhr = new XMLHttpRequest();                        
            xhr.open("POST","postMessage",true);                        
            xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");                       
            titel = document.forms["PostingWindow"]["postTitle"].value;
            content = document.forms["PostingWindow"]["postContent"].value;
            xhr.send("postId="+postId+"&postTitle="+titel+"&postContent="+content); // stuur post vars naar de server voor storage
            if(xhr.onreadystatechange===4){tryChange(titel,content);          }
}

function selectEditPost(postId, postTitle, postContent){
            // Post doen naar de server om de gekozen post om te editen duidelijk te maken.
            xhr = new XMLHttpRequest();
            xhr.open("POST","setEditPost",true);                      
            xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");                                 
            xhr.send("postId="+postId+"&postTitle="+postTitle+"&postContent="+postContent); // stuur post vars naar de server voor storage 
            xhr.onreadystatechange=tryChange(postTitle,postContent);            
}

function tryChange(titel, content){
    vulTitelEnContentVelden(titel,content);
}

function removePost(postId){
            xhr = new XMLHttpRequest();
            xhr.open("POST","deletePost",true);                      
            xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");                                 
            xhr.send("postId="+postId); // stuur post vars naar de server voor storage
            postTitle = "";
            postContent = "";
            xhr.onreadystatechange=tryChange(postTitle,postContent);   
}