package presentation;

import domain.JSON.UserToken;
import domain.User;
import service.ILoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController implements ILoginController {

    @Inject
    ILoginService loginService;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response login(User user){
        if(user == null || user.getUser() == null || user.getPassword() == null)
            return Response.status(400).entity("Please add user values!").build();

        UserToken userToken = loginService.getUserToken(user);
        if(userToken == null)
            return Response.status(401).entity("Not a valid user").build();

        return Response.ok().entity(userToken).build();
    }
}
