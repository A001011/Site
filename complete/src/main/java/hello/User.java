package hello;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "Users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String lastName;
    @Column
    private String pass;
    @Column
    private String passRepeat;
    @Column
    private String firstName;
    @Column(unique =  true)
    private String email;

    public User(String email, String pass, String firstName, String lastName) {
        this.lastName = lastName;
        this.pass = pass;
        this.firstName = firstName;
        this.email = email;
    }

public User(){

}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPassRepeat() {
        return passRepeat;
    }

    public void setPassRepeat(String passRepeat) {
        this.passRepeat = passRepeat;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", pass='" + pass + '\'' +
                ", passRepeat='" + passRepeat + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(pass, user.pass) &&
                Objects.equals(passRepeat, user.passRepeat) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, lastName, pass, passRepeat, firstName, email);
    }




}

