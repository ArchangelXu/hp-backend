package tech.mingxi.hp.backend.controllers;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple2;
import tech.mingxi.hp.backend.exceptions.HoohException;
import tech.mingxi.hp.backend.models.Post;
import tech.mingxi.hp.backend.repos.PostRepository;
import tech.mingxi.hp.backend.services.AppService;
import tech.mingxi.hp.backend.services.PostService;
import tech.mingxi.hp.backend.services.RequestHelperService;
import tech.mingxi.hp.backend.services.StorageService;
import tech.mingxi.hp.backend.utils.ErrorCode;
import tech.mingxi.hp.backend.utils.Projector;

@Slf4j
@RestController()
@RequestMapping("/posts")
public class PostController {
	final PostRepository postRepository;
	final RequestHelperService requestHelperService;
	final StorageService storageService;
	final AppService appService;
	final PostService postService;
	final Projector projector;

	public PostController(
			PostRepository postRepository,
			RequestHelperService requestHelperService,
			StorageService storageService,
			AppService appService, PostService postService,
			Projector projector
	) {
		this.postRepository = postRepository;
		this.requestHelperService = requestHelperService;
		this.storageService = storageService;
		this.appService = appService;
		this.postService = postService;
		this.projector = projector;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Post.SimpleProjection getPost(
			@PathVariable String id
	) {
		Post post = postRepository.findById(id).orElse(null);
		if (post == null) {
			throw new HoohException(
					HttpStatus.BAD_REQUEST,
					ErrorCode.RESOURCE_NOT_FOUND,
					String.format("post not found: %s", id)
			);
		}
		return projector.getProjectedObject(post, Post.SimpleProjection.class);
	}


	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Post.SimpleProjection createPost(
			@RequestHeader(name = HttpHeaders.USER_AGENT) String ua,
			@Valid @RequestBody CreatePostRequest request
	) {
		Post post = postService.createPost(request.getImageKey(), ua.toLowerCase());
		return projector.getProjectedObject(post, Post.SimpleProjection.class);
	}

	@RequestMapping(value = "/feeds", method = RequestMethod.GET)
	public List<Post.SimpleProjection> getFeeds(
			@RequestParam(name = "timestamp", required = false) Timestamp date,
			@RequestParam(name = "size", required = false) Integer size
	) {
		size = requestHelperService.getProperPageSize(size);
		date = requestHelperService.getProperUtcTimestamp(date);
		List<Post> posts = postService.getFeedsPosts(date, size);
		return projector.getProjectedList(posts, Post.SimpleProjection.class);
	}

	@RequestMapping(value = "/request-uploading-post-image", method = RequestMethod.POST)
	public AppService.RequestUploadingUrlResponse requestUploadPostImageUrl(
			@RequestBody AppService.RequestUploadingUrlRequest request
	) {
		Tuple2<String, String> tuple2 = appService.requestUploadingImageUrl(request.getMd5(), request.getExt(), StorageService.TYPE_POST);
		return new AppService.RequestUploadingUrlResponse(tuple2.getT1(), tuple2.getT2());
	}

	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	@Data
	private static class CreatePostRequest {
		private @NotEmpty String imageKey;
	}
}

