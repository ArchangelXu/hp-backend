package tech.mingxi.hp.backend.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tech.mingxi.hp.backend.models.Post;
import tech.mingxi.hp.backend.repos.PostRepository;

@Slf4j
@Service
@Transactional
public class PostService {
	private final StorageService storageService;
	private final PostRepository postRepository;

	public PostService(
			StorageService storageService,
			PostRepository postRepository
	) {
		this.storageService = storageService;
		this.postRepository = postRepository;
	}


	public Post createPost(
			String imageKey,
			String userAgent
	) {
		Post post = new Post();
		post.setImageKey(imageKey);
		if (userAgent.contains("android")) {
			post.setDeviceType(Post.DEVICE_TYPE_ANDROID);
		} else if (userAgent.contains("ios")) {
			post.setDeviceType(Post.DEVICE_TYPE_IOS);
		} else {
			post.setDeviceType(Post.DEVICE_TYPE_OTHER);
		}
		post = postRepository.save(post);
		applyImageUrls(post);
		return post;
	}

	public List<Post> getFeedsPosts(
			Timestamp date,
			Integer size
	) {
		List<Post> posts;
		posts = postRepository.findAllByCreatedAtBeforeOrderByCreatedAtDesc(
				date,
				PageRequest.of(0, size)
		);
		applyImageUrls(posts.toArray(new Post[0]));
		return posts;
	}

	public void applyImageUrls(Post... posts) {
		for (Post post : posts) {
			post.setImageUrl(storageService.getViewingUrl(post.getImageKey()));
		}
	}
}
