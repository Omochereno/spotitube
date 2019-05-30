package presentation;

import datasource.util.UtilManager;
import domain.JSON.UserToken;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ILoginService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    ILoginService serviceMock;

    @InjectMocks
    LoginController sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loginTestCorrect() {
        User user = getUser();
        UserToken token = getUserToken(user);

        when(serviceMock.getUserToken(user)).thenReturn(token);

        Response response = sut.login(user);

        verify(serviceMock,times(1)).getUserToken(user);
        assertEquals(200, response.getStatus());
    }

    @Test
    void loginTestNoUsername() {
        User user = getUser();
        user.setUser(null);

        Response reponse = sut.login(user);

        assertEquals(400, reponse.getStatus());
    }

    @Test
    void loginTestNoValidUser() {
        when(serviceMock.getUserToken(getUser())).thenReturn(null);

        Response response = sut.login(getUser());
        assertEquals(401, response.getStatus());
    }

    private UserToken getUserToken(User user) {
        UserToken userToken = new UserToken();
        userToken.setUser(user.getUser());
        userToken.setToken(UtilManager.generateToken(user.getUser()));
        return userToken;
    }

    private User getUser() {
        User user = new User();
        user.setUser("testUser");
        user.setPassword("password");
        return user;
    }
}
