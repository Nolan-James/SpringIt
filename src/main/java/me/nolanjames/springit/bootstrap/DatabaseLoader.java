package me.nolanjames.springit.bootstrap;

import me.nolanjames.springit.model.Link;
import me.nolanjames.springit.repository.CommentRepository;
import me.nolanjames.springit.repository.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> links = new HashMap<>();
        links.put("Properties with Spring and Spring Boot", "https://www.baeldung.com/properties-with-spring");

        links.forEach((k, v) -> {
            linkRepository.save(new Link(k, v));
        });

        long linkCount = linkRepository.count();
        System.out.println(linkCount);
    }
}
