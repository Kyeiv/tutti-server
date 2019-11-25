package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Post;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.controller.utils.ResponseFactory;
import pl.com.tutti.tuttiserver.rest.exception.PostExistsException;
import pl.com.tutti.tuttiserver.rest.exception.PostNotExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.rest.data.PostData;
import pl.com.tutti.tuttiserver.service.PostsService;
import pl.com.tutti.tuttiserver.service.UsersService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BlogRestController {
    private final PostsService postsService;
    private final UsersService usersService;

    @GetMapping("/posts/{username}")
    ResponseEntity getPosts(@PathVariable("username") String username){

        Users user = usersService.findByUsername(username);
        user = usersService.getWithPosts(user);

        List<Post> posts = user.getPosts();
        if(posts == null)
            posts = new ArrayList();
        else
            posts.stream().forEach(spec -> spec.setUsername(null));

        return ResponseFactory.createPayloadResponse(posts, "getPosts");
    }

    @PostMapping("/posts")
    public ResponseEntity addPost(@Valid @RequestBody PostData postData, Principal principal){
        if(postData.getId() != null)
            throw new PostExistsException("To update existing use PATCH!");

        return getResponseEntity(postData, principal);
    }

    @PatchMapping("/posts")
    public ResponseEntity updatePost(@Valid @RequestBody PostData postData, Principal principal) {
        if(postData.getId() == null)
            throw new PostNotExistsException("To save new use POST!");

        Post post = postsService.findById(postData.getId());
        if(!post.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to patch this entry!");

        post.setContent(postData.getContent());
        post.setCreatedAt(postData.getCreatedAt());
        post.setTitle(postData.getTitle());

        postsService.save(post);

        return ResponseFactory.createBasicResponse("updatePost");
    }

    @DeleteMapping("/posts/{id}")
    @ResponseBody
    public ResponseEntity deletePost(@PathVariable("id") Integer id, Principal principal) {
        Post post = postsService.findById(id);
        if(!post.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to delete this entry!");

        postsService.delete(post);

        return ResponseFactory.createBasicResponse("deletePost");
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid PostData postData, Principal principal) {
        Users user = usersService.findByUsername(principal.getName());

        Post post = new Post();
        post.setUsername(user);
        post.setContent(postData.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setTitle(postData.getTitle());

        user = usersService.getWithPosts(user);
        if(user.getPosts() == null)
            user.setPosts(new ArrayList<>());
        user.addPost(post);

        postsService.save(post);

        return ResponseFactory.createPayloadResponse(post.getId(), "addPost");
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity getPost(@PathVariable("id") Integer id, Principal principal) {
        Post post = postsService.findById(id);

        PostData postData = new PostData();
        postData.setId(post.getId());
        postData.setContent(post.getContent());
        postData.setTitle(post.getTitle());
        postData.setCreatedAt(post.getCreatedAt());
        postData.setUsername(post.getUsername().getUsername());

        return ResponseFactory.createPayloadResponse(postData, "getPost");
    }
}
