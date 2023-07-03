package study.awsdemo.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.awsdemo.domain.post.Post;
import study.awsdemo.domain.post.service.PostService;
import study.awsdemo.domain.post.repository.PostRepository;
import study.awsdemo.domain.post.controller.dto.PostDto;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/bbs")
public class PostController {
    private final PostRepository postRepository;
    private final PostService PostService;

    @GetMapping("posts")
    public List<PostDto> getPosts() {
        List<Post> findAll = postRepository.findAll();
        List<PostDto> postDtoList = findAll.stream()
                .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent()))
                .toList();
        return postDtoList;
    }

    @PostMapping("posts/create")
    public PostDto createPost(@RequestBody PostDto request) {
        Post post = new Post(request.getTitle(), request.getContent());
        postRepository.save(post);
        return new PostDto(post.getId(), post.getTitle(), post.getContent());
    }

    @DeleteMapping("posts/{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        if (byId.isPresent()) {
            Post findPost = byId.get();
            postRepository.delete(findPost);
        }
    }

    @PatchMapping("posts")
    public PostDto updatePost(@RequestBody PostDto request) {
        Optional<Post> byId= postRepository.findById(request.getId());
        if(byId.isPresent()){
            Post updatePost=byId.get();
            updatePost.setTitle(request.getTitle());
            updatePost.setContent(request.getContent());
            postRepository.save(updatePost);
            return new PostDto(request.getId(), updatePost.getTitle(), updatePost.getContent());
        }
        return new PostDto();
    }
}
