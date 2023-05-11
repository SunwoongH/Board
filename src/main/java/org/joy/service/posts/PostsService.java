package org.joy.service.posts;

import lombok.RequiredArgsConstructor;
import org.joy.domain.posts.Posts;
import org.joy.domain.posts.PostsRepository;
import org.joy.web.dto.request.PostsSaveRequestDto;
import org.joy.web.dto.request.PostsUpdateRequestDto;
import org.joy.web.dto.response.PostsInfoResponseDto;
import org.joy.web.dto.response.PostsResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<PostsInfoResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .stream()
                .map(PostsInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts findPosts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id = " + id + " 게시글이 존재하지 않습니다."));
        postsRepository.delete(findPosts);
    }
}
