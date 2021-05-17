package com.example.boke.Controller.admin;

import com.example.boke.Model.Tag;
import com.example.boke.Model.Type;
import com.example.boke.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", tagService.ListTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @Transactional
    @PostMapping("/tags")
    public String inputPost(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes){
        Tag tagname = tagService.getTagByName(tag.getName());
        if(tagname != null){
            bindingResult.rejectValue("name","nameError","标签已存在，不能重复添加");
        }
        if(bindingResult.hasErrors()) return "admin/tags-input";
        Tag tag1 = tagService.saveTag(tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Tag tag = tagService.gettag(id);
        model.addAttribute("tag", tag);
        return "admin/tags-edit";
    }

    @Transactional
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes,@PathVariable Long id){

        Tag tagname = tagService.getTagByName(tag.getName());
        if(tagname != null){
            bindingResult.rejectValue("name","nameError","标签已存在，不能重复添加");
        }
        if(bindingResult.hasErrors()) return "admin/tags-input";
        Tag tag1 = tagService.update(id, tag);
        if(tag1 == null){
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
