package me.nolanjames.springit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.nolanjames.springit.service.BeanUtil;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import static me.nolanjames.springit.util.DateUtil.convertToDateViaInstant;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Link extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotEmpty(message = "Please enter a title.")
    private String title;

    @NonNull
    @NotEmpty(message = "Please enter a url.")
    @URL(message = "Please enter a valid url.")
    private String url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();

    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(this.url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public String getPrettyTime() {
        PrettyTime pt = BeanUtil.getBean(PrettyTime.class);
        return pt.format(convertToDateViaInstant(getCreationDate()));
    }

}
