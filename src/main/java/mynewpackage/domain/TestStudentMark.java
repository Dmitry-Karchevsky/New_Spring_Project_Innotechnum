package mynewpackage.domain;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class TestStudentMark {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(Views.RequiredField.class)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Test test;

    private Integer countOfQuestions;

    private Integer countOfCorrectAnswers;

    private Double mark;

    private boolean isFinish;

    public TestStudentMark() {
    }

    public TestStudentMark(User user, Test test, Integer countOfQuestions, boolean isFinish) {
        this.user = user;
        this.test = test;
        this.countOfQuestions = countOfQuestions;
        this.isFinish = isFinish;
        countOfCorrectAnswers = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(Integer countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    public Integer getCountOfCorrectAnswers() {
        return countOfCorrectAnswers;
    }

    public void setCountOfCorrectAnswers(Integer countOfCorrectAnswers) {
        this.countOfCorrectAnswers = countOfCorrectAnswers;
    }
}
