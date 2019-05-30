package service;

import datasource.ILoginDAO;
import datasource.util.UtilManager;
import domain.JSON.UserToken;
import domain.User;

import javax.inject.Inject;

public class LoginService implements ILoginService {

    @Inject
    ILoginDAO loginDAO;

    @Override
    public UserToken getUserToken(User user) {
        UserToken userToken = null;
        if(loginDAO.isvalidUser(user)) {
            userToken = new UserToken();
            userToken.setUser(user.getUser());
            userToken.setToken(UtilManager.generateToken(user.getUser()));
        }
        return userToken;
    }
}
