package com.example.boke.Repository;

import com.example.boke.Model.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    @Modifying
    @Query("update T_type set name = :name where id = :id")
    void updateType(@Param("id") Long id, @Param("name") String name);

    Type findByName(String name);

    @Query("select t from T_type t")
    List<Type> findTop(Pageable pageable);
}
