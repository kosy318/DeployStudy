package com.example.deploytest.springboot.web.domain.posts;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.deploytest.springboot.domain.posts.Posts;
import com.example.deploytest.springboot.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    final String title = "테스트 게시글";
    final String content = "테스트 본문";

    @Test
    public void 게시글저장_불러오기() {
        // given
        postsRepository.save(Posts.builder().title(title).content(content).author("soo").build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0 );
        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author("author")
                        .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>> CreateDate = " + posts.getCreatedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
