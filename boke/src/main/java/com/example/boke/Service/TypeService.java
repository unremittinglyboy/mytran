package com.example.boke.Service;

import com.example.boke.Model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    Type gettype(Long id);

    Page<Type> ListType(Pageable pageable);

    List<Type> listType();

    //根据传的值来设置数据列表的大小
    List<Type> listTypeTop(Integer size);

    Type update(Long id, Type type);

    Type getTypeByName(String name);

    void deleteType(Long id);
}
