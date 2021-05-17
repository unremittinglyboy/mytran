package com.example.boke.Service;

import com.example.boke.Model.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId, Sort sort);
    Comment saveComment(Comment comment);
}
