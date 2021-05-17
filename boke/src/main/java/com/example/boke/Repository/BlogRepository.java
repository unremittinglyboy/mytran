package com.example.boke.Repository;

import com.example.boke.Model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from t_blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //select * from t_blog where title like '%content%' controller使用该方法进行模糊查询要加上前后的百分号
    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);
}
