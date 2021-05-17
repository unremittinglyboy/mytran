package com.example.boke.Service;

import com.example.boke.Model.Blog;
import com.example.boke.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> ListBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> ListBlog(Pageable pageable);

    Page<Blog> ListBlog(Pageable pageable,String query);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog getAndConvert(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deteleBlog(Long id);
}
