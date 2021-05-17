package com.example.boke.Controller;

import com.example.boke.Model.Blog;
import com.example.boke.Service.BlogService;
import com.example.boke.Service.TagService;
import com.example.boke.Service.TypeService;
import com.example.boke.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", blogService.ListBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", typeService.listTypeTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));


        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("page",blogService.ListBlog(pageable,"%" + query + "%"));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        Blog blog = blogService.getAndConvert(id);
        blog.setViews(blog.getViews() + 1);
        Blog updateBlog = blogService.updateBlog(id, blog);
        model.addAttribute("blog", updateBlog);
        return "blog";
    }
}
