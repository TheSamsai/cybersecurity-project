package sec.project.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> implements Serializable {

    @Column(unique = true)
    private String username;
    private String password;
    
    @OneToMany(mappedBy = "account")
    private List<Message> messages;
    
    public Account() {
        super();
    }

    public Account(String name, String password) {
        this();
        this.username = name;
        this.password = password;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String address) {
        this.password = address;
    }
}
