package com.example.boke.Service;

import com.example.boke.Model.Tag;
import com.example.boke.Model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag gettag(Long id);

    Page<Tag> ListTag(Pageable pageable);

    Tag update(Long id, Tag tag);

    Tag getTagByName(String name);

    void deleteTag(Long id);

    List<Tag> ListTag();

    List<Tag> ListTagTop(Integer size);

    List<Tag> listTag(String ids);
}
