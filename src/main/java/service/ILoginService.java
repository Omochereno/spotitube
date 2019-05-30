package service;

import domain.JSON.UserToken;
import domain.User;

public interface ILoginService {

    UserToken getUserToken(User user);
}
