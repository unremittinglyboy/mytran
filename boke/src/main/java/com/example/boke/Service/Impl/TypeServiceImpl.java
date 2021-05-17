package com.example.boke.Service.Impl;

import com.example.boke.Exception.NotFoundException;
import com.example.boke.Model.Type;
import com.example.boke.Repository.TypeRepository;
import com.example.boke.Service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    @Transactional
    public Type gettype(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    @Transactional
    public Page<Type> ListType(Pageable pageable) {
        return typeRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    @Override
    @Transactional
    public Type update(Long id, Type type) {
        Type one = typeRepository.getOne(id);
        if(one == null) {
            throw new NotFoundException("不存在该类型");
        }
        typeRepository.updateType(id, type.getName());
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
