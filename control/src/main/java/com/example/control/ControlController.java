package com.example.control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
public class ControlController {

    List<Post> posts = new ArrayList<>();

    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(@RequestBody Post post){
        posts.add(post);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Integer id){
        return ResponseEntity.ok(posts.get(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Integer id, @RequestBody Post newPost){
        posts.set(id, newPost);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id){
        posts.remove(posts.get(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts")
    public ResponseEntity<Void> deleteAllPosts(){
        for(Post post: posts){
            posts.remove(post);
        }
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/count")
    public ResponseEntity<Integer> getSize(){
        return ResponseEntity.ok(posts.size());
    }

    @GetMapping("/posts/author/{author}")
    public ResponseEntity<List<Post>> getAuthorPosts(@PathVariable String author){
        List <Post> authorPost = new ArrayList<>();
        for(Post post: posts){
            if(post.getAuthor().contains(author)){
                authorPost.add(post);
            }
        }
        return ResponseEntity.ok(authorPost);
    }

    @GetMapping("/posts/latest")
    public ResponseEntity<List<Post>> getLatestPosts(){
        List <Post> sortingPost = new ArrayList<>(posts);
        boolean sorted = true;
        while(sorted){
            sorted = false;
            for(int i = 1; i<sortingPost.size(); i++){
                if(sortingPost.get(i).getCreatedAt().isBefore(sortingPost.get(i-1).getCreatedAt())){
                    Post post = new Post();
                    post = sortingPost.get(i);
                    sortingPost.set(i, (sortingPost.get(i-1)));
                    sortingPost.set(i-1, post);
                    sorted = true;
                }
            }
        }
        List<Post> latestPosts = IntStream.iterate(sortingPost.size() - 21, i -> i < sortingPost.size(), i -> i - 1).mapToObj(sortingPost::get).collect(Collectors.toList());
        return ResponseEntity.ok(latestPosts);
    }

    @GetMapping("/posts/cheapest")
    public ResponseEntity<List<Post>> getCheapestPost(){
        List <Post> sortingPost = new ArrayList<>(posts);
        boolean sorted = true;
        while(sorted){
            sorted = false;
            for(int i = 1; i<sortingPost.size(); i++){
                if(sortingPost.get(i).getPrice()<sortingPost.get(i-1).getPrice()){
                    Post post = new Post();
                    post = sortingPost.get(i);
                    sortingPost.set(i, (sortingPost.get(i-1)));
                    sortingPost.set(i-1, post);
                    sorted = true;
                }
            }
        }
        return ResponseEntity.ok(sortingPost);
    }

    @GetMapping("/posts/expensive ")
    public ResponseEntity<List<Post>> getExpensivePost(){
        List <Post> sortingPost = new ArrayList<>(posts);
        boolean sorted = true;
        while(sorted){
            sorted = false;
            for(int i = 1; i<sortingPost.size(); i++){
                if(sortingPost.get(i).getPrice()>sortingPost.get(i-1).getPrice()){
                    Post post = new Post();
                    post = sortingPost.get(i);
                    sortingPost.set(i, (sortingPost.get(i-1)));
                    sortingPost.set(i-1, post);
                    sorted = true;
                }
            }
        }
        return ResponseEntity.ok(sortingPost);
    }

    @GetMapping("/posts/today")
    public ResponseEntity<List<Post>> getTodayPosts(){
        List <Post> todayList = new ArrayList<>();
        for(Post post :posts){
            if(post.getCreatedAt().isEqual(LocalDateTime.now())){
                todayList.add(post);
            }
        }
        return ResponseEntity.ok(todayList);
    }

    @GetMapping("/posts/search/{word}")
    public ResponseEntity<Boolean> searchWord(@PathVariable String word){
        for(Post post: posts){
            if(post.getTitle().toLowerCase().contains(word.toLowerCase()) || post.getMessage().toLowerCase().contains(word.toLowerCase())){
                return ResponseEntity.ok(true);
            }
        }
        return  ResponseEntity.ok(false);
    }
}