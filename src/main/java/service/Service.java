package service;

import datasource.IPlaylistDAO;
import datasource.ITrackDAO;
import datasource.LoginDAO;
import domain.User;
import domain.UserToken;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PlaylistService implements IPlaylistService{

    @Inject
    private ITrackDAO trackDAO;

    @Inject
    private IPlaylistDAO playlistDAO;

    @Inject
    private LoginDAO loginDAO;


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(User user){
        UserToken userToken = null;
        if(user == null)
            return Response.status(400).entity("Please add user values!").build();

        if(loginDAO.isvalidUser(user)) {
            userToken = new UserToken();
            userToken.setUser(user.getUsername());
            userToken.setToken("1234-1234-1234");
        }

        return Response.ok().entity(userToken).build();

    }

}
