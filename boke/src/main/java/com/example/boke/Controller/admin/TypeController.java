package com.example.boke.Controller.admin;

import com.example.boke.Model.Type;
import com.example.boke.Service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                       Pageable pageable,
                       Model model){
        model.addAttribute("page", typeService.ListType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @Transactional
    //若写的是一个对象，则获取到的对应属性会封装到该对象中。
    @PostMapping("/types")
    public String inputPost(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName()); //检验类型名是否已经存在
        if(type1 != null){
            result.rejectValue("name", "nameError", "分类已存在，不能重复添加"); //自定义重复类错误提示的信息
        }

        if(result.hasErrors()) return "admin/types-input";
        Type t = typeService.saveType(type);

        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
            return "redirect:/admin/types";

    }

    @GetMapping("/types/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Type type = typeService.gettype(id);
        model.addAttribute("type", type);
        return "admin/types-edit";
    }

    @Transactional
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName()); //检验类型名是否已经存在
        if(type1 != null){
            result.rejectValue("name", "nameError", "分类已存在，不能重复添加"); //自定义重复类错误提示的信息
        }

        if(result.hasErrors()){
            return "admin/types-edit";
        }
        Type t = typeService.update(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
