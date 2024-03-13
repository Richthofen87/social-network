package ru.skillbox.diplom.group46.social.network.impl.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.Type;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostScheduler {

    private final PostRepository postRepository;

    @Scheduled(cron = "0 * * * * *")
    public void checkScheduledPosts() {
        List<Post> queuedPosts = postRepository.findByType(Type.QUEUED);
        ZonedDateTime currentTime = ZonedDateTime.now();
        List<Post> postsToUpdate = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        for (Post post : queuedPosts) {
            ZonedDateTime postTime = post.getPublishDate();

            String formattedPostTime = postTime.format(formatter);
            String formattedCurrentTime = currentTime.format(formatter);

            if (formattedPostTime.compareTo(formattedCurrentTime) < 0) {
                post.setType(Type.POSTED);
                post.setTimeChanged(ZonedDateTime.now());
                postsToUpdate.add(post);
            }
        }

        if (!postsToUpdate.isEmpty()) {
            postRepository.saveAll(postsToUpdate);
        }
    }
}