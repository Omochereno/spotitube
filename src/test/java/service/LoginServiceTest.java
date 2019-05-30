package service;

import datasource.LoginDAO;
import domain.JSON.UserToken;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class LoginServiceTest {

    @Mock
    private LoginDAO daoMock;

    @InjectMocks
    private LoginService sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getUserTokenValid() {
        User user = getTestUser();

        when(daoMock.isvalidUser(user)).thenReturn(true);

        UserToken token = sut.getUserToken(user);
        verify(daoMock, times(1)).isvalidUser(user);
        assertEquals(token.getUser(), user.getUser());
    }

    @Test
    void getUserTokenFail() {
        when(daoMock.isvalidUser(getTestUser())).thenReturn(false);

        UserToken token = sut.getUserToken(getTestUser());
        assertEquals(token, null);
    }

    private User getTestUser(){
        User user = new User();
        user.setUser("Jaha");
        user.setPassword("test");
        return user;
    }
}