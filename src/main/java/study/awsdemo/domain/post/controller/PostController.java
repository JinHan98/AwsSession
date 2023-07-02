package study.awsdemo.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.awsdemo.domain.post.Post;
import study.awsdemo.domain.post.PostRepository;
import study.awsdemo.domain.post.controller.dto.PostDto;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/bbs")
public class PostController {
    private final PostRepository postRepository;

    @GetMapping("posts")
    public List<PostDto> getPosts() {
        List<Post> findAll = postRepository.findAll();
        List<PostDto> postDtoList = findAll.stream()
                .map(post -> new PostDto(post.getTitle(), post.getContent()))
                .toList();
        return postDtoList;
    }

    @PostMapping("posts/create")
    public void createPost(@RequestBody PostDto request) {
        Post post = new Post(request.getTitle(), request.getContent());
        postRepository.save(post);
    }

    @DeleteMapping("posts/{id}")
    public void deletePost(@PathVariable Long id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent()) {
            Post findPost = byId.get();
            postRepository.delete(findPost);
        }
    }
}
