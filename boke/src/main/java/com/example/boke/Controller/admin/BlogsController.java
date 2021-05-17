package com.example.boke.Controller.admin;

import com.example.boke.Model.Blog;
import com.example.boke.Model.Type;
import com.example.boke.Model.User;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogsController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String list(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.ListBlog(pageable,blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.ListBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags",tagService.ListTag());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String post(@Valid Blog blog, BindingResult result, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.gettype(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        if(result.hasErrors()) return "admin/blogs-input";
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return"redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags",tagService.ListTag());
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blogs-edit";
    }

    @Transactional
    @PostMapping("/blogs/{id}")
    public String editPost(@Valid Blog blog, BindingResult result, @PathVariable Long id, HttpSession session,RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.gettype(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        if(result.hasErrors()) return "admin/blogs-edit";

        Blog b = blogService.updateBlog(id, blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return"redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deteleBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}
