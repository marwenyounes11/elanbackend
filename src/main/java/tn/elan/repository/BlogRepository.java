package tn.elan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Blog;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{

}
