package domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private String user;
    private String password;

//    public User(String user, String password){
//        this.user = user;
//        this.password = password;
//    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
