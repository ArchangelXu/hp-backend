package tech.mingxi.hp.backend.repos;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import tech.mingxi.hp.backend.models.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, String> {
	List<Post> findAllByCreatedAtBeforeOrderByCreatedAtDesc(Timestamp timestamp,Pageable pageable);
}


