package com.example.bookstore;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginPageAccessible() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    public void booklistRequiresLogin() throws Exception {
        mockMvc.perform(get("/booklist"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void userCanAccessBooklist() throws Exception {
        mockMvc.perform(get("/booklist")
            .with(user("user").roles("USER")))
            .andExpect(status().isOk());
    }

    @Test
    public void userCannotDelete() throws Exception {
        mockMvc.perform(get("/delete/1")
            .with(user("user").roles("USER")))
            .andExpect(status().isForbidden());
    }

    @Test
    public void adminCanDelete() throws Exception {
        mockMvc.perform(get("/delete/1")
            .with(user("admin").roles("ADMIN")))
            .andExpect(status().is3xxRedirection()); // Redirects after delete
    }
} 