package top.fsky.crawler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class AppController {
    @GetMapping
    public String helloChange() {
        return "Hello Web MVC!";
    }
}
