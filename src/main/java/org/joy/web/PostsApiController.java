package org.joy.web;

import lombok.RequiredArgsConstructor;
import org.joy.service.posts.PostsService;
import org.joy.web.dto.request.PostsSaveRequestDto;
import org.joy.web.dto.request.PostsUpdateRequestDto;
import org.joy.web.dto.response.PostsResponseDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/posts")
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        return postsService.save(postsSaveRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) {
        return postsService.update(id, postsUpdateRequestDto);
    }

    @GetMapping("/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
