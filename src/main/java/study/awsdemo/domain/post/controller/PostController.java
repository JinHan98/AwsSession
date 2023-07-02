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
                .map(post -> new PostDto(post.getTitle(), post.getContent()))
                .toList();
        return postDtoList;
    }

    @PostMapping("posts/create")
    public PostDto createPost(@RequestBody PostDto request) {
        Post post = new Post(request.getTitle(), request.getContent());
        postRepository.save(post);
        return new PostDto(post.getTitle(), post.getContent());
    }

    @DeleteMapping("posts/{id}")
    public void deletePost(@PathVariable Long id) {
        System.out.println(id);

        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent()) {
            Post findPost = byId.get();
            postRepository.delete(findPost);
        }
    }

//    @PatchMapping("posts/{id}")
//    public PostDto updatePost(@RequestBody PostDto request, @PathVariable Long id) {
//
//    }
}
