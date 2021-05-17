package com.example.boke.Service.Impl;

import com.example.boke.Exception.NotFoundException;
import com.example.boke.Model.Tag;
import com.example.boke.Model.Type;
import com.example.boke.Repository.TagRepository;
import com.example.boke.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag gettag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> ListTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag update(Long id, Tag tag) {
        Tag one = tagRepository.getOne(id);
        if(one == null) {
            throw new NotFoundException("不存在该类型");
        }
        tagRepository.updateTag(id, tag.getName());
        return tagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> ListTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> ListTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        List<Long> longs = convertToList(ids);
        return tagRepository.findAllById(longs);
    }

    //将string转换为一个long型数组
    private List<Long> convertToList(String ids){
        ArrayList<Long> list = new ArrayList<>();
        if(!"".equals(ids)){
            String[] idarray = ids.split(",");
            for(int i = 0; i < idarray.length; i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

}
