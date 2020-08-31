package mynewpackage.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(Views.RequiredField.class)
    private Long id;

    @NotNull//не работает
    @Size(min=3, message = "Не меньше 3 знаков")
    @JsonView(Views.RequiredField.class)
    private String username;

    //@NotNull
    @JsonView(Views.RequiredField.class)
    private String firstName;

    //@NotNull
    @JsonView(Views.RequiredField.class)
    private String lastName;

    //@NotNull
    @JsonView(Views.RequiredField.class)
    private String email;

    //@NotNull
    @JsonView(Views.RequiredField.class)
    private LocalDate regdate;

    @JsonView(Views.RequiredField.class)
    private String organization;

    @NotNull
    @Size(min=3, message = "Не меньше 3 знаков")
    @JsonView(Views.RequiredField.class)
    private String password;

    @Transient
    @JsonView(Views.NotRequiredField.class)
    private String passwordConfirm;

    //@NotNull
    @JsonView(Views.NotRequiredField.class)
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView(Views.RequiredField.class)
    private Set<Role> roles;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDate regdate) {
        this.regdate = regdate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
