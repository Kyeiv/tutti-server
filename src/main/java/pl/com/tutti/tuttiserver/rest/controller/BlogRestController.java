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
import pl.com.tutti.tuttiserver.rest.data.PostData;
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

        ElementIdResponse elementIdResponse = new ElementIdResponse();
        elementIdResponse.setMessage("addPost");
        elementIdResponse.setStatus(HttpStatus.OK.value());
        elementIdResponse.setTimeStamp(System.currentTimeMillis());
        elementIdResponse.setElementId(post.getId());

        return new ResponseEntity<>(elementIdResponse, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity getPost(@PathVariable("id") Integer id, Principal principal) {
        Post post = postsService.findById(id);
        if(!post.getUsername().getUsername().equals(principal.getName()))
            throw new UnauthorizedException("No rights to get this entry!");

        PostData postData = new PostData();
        postData.setId(post.getId());
        postData.setContent(post.getContent());
        postData.setTitle(post.getTitle());
        postData.setCreatedAt(post.getCreatedAt());

        PostDataResponse postDataResponse = new PostDataResponse();
        postDataResponse.setMessage("getPost");
        postDataResponse.setStatus(HttpStatus.OK.value());
        postDataResponse.setTimeStamp(System.currentTimeMillis());
        postDataResponse.setPostData(postData);

        return new ResponseEntity<>(postDataResponse, HttpStatus.OK);
    }
}
