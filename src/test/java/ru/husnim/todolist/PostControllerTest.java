package ru.husnim.todolist;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.husnim.todolist.controller.PostController;
import ru.husnim.todolist.dto.PostDTO;
import ru.husnim.todolist.model.Post;
import ru.husnim.todolist.service.PostService;

public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PostService service;

    @InjectMocks
    private PostController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllPosts() throws Exception {
        Post post1 = new Post("Post 1", "Content 1");
        Post post2 = new Post("Post 2", "Content 2");
        List<Post> posts = Arrays.asList(post1, post2);

        when(service.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[1].title").value("Post 2"));
    }

    @Test
    public void testCreatePost() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("New Post");
        postDTO.setContent("New Content");

        Post createdPost = new Post("New Post", "New Content");

        when(service.createPost(any(PostDTO.class))).thenReturn(createdPost);

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Post\",\"content\":\"New Content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Post"));
    }

    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post("Post 1", "Content 1");

        when(service.getPostById(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Post 1"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(service).deletePost(1L);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }

}
