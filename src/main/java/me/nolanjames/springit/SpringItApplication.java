package me.nolanjames.springit;

import me.nolanjames.springit.model.Comment;
import me.nolanjames.springit.model.Link;
import me.nolanjames.springit.repository.CommentRepository;
import me.nolanjames.springit.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class SpringItApplication {

    public static final Logger log = LoggerFactory.getLogger(SpringItApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringItApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
        return args -> {
            Link link = new Link("Properties with Spring and Spring Boot", "https://www.baeldung.com/properties-with-spring");

            Comment comment = new Comment("Great resource");
            link.getComments().add(comment);
            linkRepository.save(link);
        };
    }

}
