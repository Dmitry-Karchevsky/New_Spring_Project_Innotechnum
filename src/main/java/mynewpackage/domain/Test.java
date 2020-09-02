package mynewpackage.domain;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(Views.RequiredField.class)
    private Long id;

    @JsonView(Views.RequiredField.class)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    @JsonView(Views.RequiredField.class)
    private User author;

    public Test() {}

    public Test(String name, User author) {
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(id, test.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
