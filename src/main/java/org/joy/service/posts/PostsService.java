package org.joy.service.posts;

import lombok.RequiredArgsConstructor;
import org.joy.domain.posts.Posts;
import org.joy.domain.posts.PostsRepository;
import org.joy.web.dto.request.PostsSaveRequestDto;
import org.joy.web.dto.request.PostsUpdateRequestDto;
import org.joy.web.dto.response.PostsResponseDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {
        Posts findPosts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id = " + id + " 게시글이 존재하지 않습니다."));
        findPosts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts findPosts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id = " + id + " 게시글이 존재하지 않습니다."));
        return new PostsResponseDto(findPosts);
    }
}