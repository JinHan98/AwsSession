package study.awsdemo.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.awsdemo.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
