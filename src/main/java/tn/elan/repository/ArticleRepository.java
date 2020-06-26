package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.elan.model.Article;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
