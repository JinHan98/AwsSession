package study.awsdemo.domain.post.service;

import study.awsdemo.domain.post.Post;
import study.awsdemo.domain.post.controller.dto.PostDto;

import java.util.List;

public interface PostService {
//    public PostDto createPost(PostDto request);

//    public List<PostDto> getPosts();

    public void deletePost(Long id);

}
