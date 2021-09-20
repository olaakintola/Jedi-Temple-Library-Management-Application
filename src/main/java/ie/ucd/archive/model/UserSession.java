package ie.ucd.archive.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private User user;
    private Boolean isLoginFailed;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getLoginFailed() {
        return isLoginFailed;
    }

    public void setLoginFailed(Boolean loginFailed) {
        isLoginFailed = loginFailed;
    }
}