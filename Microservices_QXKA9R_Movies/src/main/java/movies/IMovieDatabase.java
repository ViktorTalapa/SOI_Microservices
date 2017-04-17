package movies;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import movies.Movies.*;

@Path("")
@Consumes("application/x-protobuf")
@Produces("application/x-protobuf")
public interface IMovieDatabase {

    @GET
    @Path("movies")
    Response getAll();

    @GET
    @Path("movies/{id}")
    Response get(@PathParam("id") int id);

    @POST
    @Path("movies")
    Response add(InputStream input);

    @PUT
    @Path("movies/{id}")
    void update(@PathParam("id") int id, InputStream input);

    @DELETE
    @Path("movies/{id}")
    void delete(@PathParam("id") int id);

    @GET
    @Path("movies/find")
    Response query(@QueryParam("year") int year, @QueryParam("orderby") String orderBy);
}
