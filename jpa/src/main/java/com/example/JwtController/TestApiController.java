package com.example.JwtController;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TestApiController {
    @GetMapping("/all")
    public String allAccess()   {
        return "Đây là trang mọi người có thể truy cập";
    }
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/user")
    public String userAccess()  {
        return "Đây là trang người dùng";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String mederatorAccess()   {
        return "Đây là trang mod";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess()   {
        return "Đây là trang admin";
    }
    @GetMapping("/post")
    @PreAuthorize("hasRole('USER')")
    public String post()    {
        return "Trang dang bai";
    }

}
