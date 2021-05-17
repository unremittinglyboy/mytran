package com.example.boke.Controller;
import com.example.boke.Model.Comment;
import com.example.boke.Service.BlogService;
import com.example.boke.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @author ych
 * @date 20/4/2020 6:48 PM
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

//    @Value(value = "${avatar}")
//    private String avatar = "/images/avatar.png";

    //    @Transactional
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Comment> commentList = commentService.listCommentByBlogId(blogId,sort);
        model.addAttribute("comments", commentList);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
//        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
