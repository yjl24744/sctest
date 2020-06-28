package com.yang.auth.rest;

import com.yang.auth.entity.Blog;
import com.yang.auth.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    IBlogService blogService;

    @GetMapping
    public List<Blog> list() {
        return blogService.getBlogs();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/delete/{id}")
    public List<Blog> delete(@PathVariable("id") Long id) {
        blogService.deleteBlog(id);
        return blogService.getBlogs();
    }
}
