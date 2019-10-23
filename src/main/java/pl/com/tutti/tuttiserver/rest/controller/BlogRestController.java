package pl.com.tutti.tuttiserver.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tutti.tuttiserver.entity.Post;
import pl.com.tutti.tuttiserver.entity.Users;
import pl.com.tutti.tuttiserver.rest.exception.PostExistsException;
import pl.com.tutti.tuttiserver.rest.exception.PostNotExistsException;
import pl.com.tutti.tuttiserver.rest.exception.UnauthorizedException;
import pl.com.tutti.tuttiserver.rest.request.PostRequest;
import pl.com.tutti.tuttiserver.rest.response.*;
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

        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setMessage("getPosts");
        postsResponse.setStatus(HttpStatus.OK.value());
        postsResponse.setTimeStamp(System.currentTimeMillis());
        postsResponse.setPosts(posts);

        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity addPost(@Valid @RequestBody PostRequest postRequest, Principal principal){
        if(postRequest.getId() != null)
            throw new PostExistsException("To update existing use PATCH!");

        return getResponseEntity(postRequest, principal);
    }

    @PatchMapping("/posts")
    public ResponseEntity updatePost(@Valid @RequestBody PostRequest postRequest, Principal principal) {
        if(postRequest.getId() == null)
            throw new PostNotExistsException("To save new use POST!");

        Post post = postsService.findById(postRequest.getId());
        if(!post.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to patch this entry!");

        post.setContent(postRequest.getContent());
        post.setCreatedAt(postRequest.getCreatedAt());
        post.setTitle(postRequest.getTitle());

        postsService.save(post);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("updatePost");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseBody
    public ResponseEntity deletePost(@PathVariable("id") Integer id, Principal principal) {
        Post post = postsService.findById(id);
        if(!post.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to delete this entry!");

        postsService.delete(post);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("deletePost");
        basicResponse.setStatus(HttpStatus.OK.value());
        basicResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    private ResponseEntity getResponseEntity(@RequestBody @Valid PostRequest postRequest, Principal principal) {
        Users user = usersService.findByUsername(principal.getName());

        Post post = new Post();
        post.setUsername(user);
        post.setContent(postRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setTitle(postRequest.getTitle());

        user = usersService.getWithPosts(user);
        if(user.getPosts() == null)
            user.setPosts(new ArrayList<>());
        user.addPost(post);

        postsService.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setMessage("addPost");
        postResponse.setStatus(HttpStatus.OK.value());
        postResponse.setTimeStamp(System.currentTimeMillis());
        postResponse.setElementId(post.getId());

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity getPost(@PathVariable("id") Integer id, Principal principal) {
        Post post = postsService.findById(id);
        if(!post.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to get this entry!");

        PostRequest postRequest = new PostRequest();
        postRequest.setId(post.getId());
        postRequest.setContent(post.getContent());
        postRequest.setTitle(post.getTitle());
        postRequest.setCreatedAt(post.getCreatedAt());

        PostBodyResponse postBodyResponse = new PostBodyResponse();
        postBodyResponse.setMessage("getPost");
        postBodyResponse.setStatus(HttpStatus.OK.value());
        postBodyResponse.setTimeStamp(System.currentTimeMillis());
        postBodyResponse.setPostRequest(postRequest);

        return new ResponseEntity<>(postBodyResponse, HttpStatus.OK);
    }
}
