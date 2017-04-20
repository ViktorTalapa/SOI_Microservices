package movies;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import movies.Movies.*;

@Path("movies")
public interface IMovieDatabase {

    @GET
    @Produces("application/x-protobuf")
    MovieList getAll();

    @GET
    @Path("{id}")
    @Produces("application/x-protobuf")
    Response get(@PathParam("id") int id);

    @POST
    @Consumes("application/x-protobuf")
    @Produces("application/x-protobuf")
    Response add(InputStream input);

    @PUT
    @Path("{id}")
    @Consumes("application/x-protobuf")
    void update(@PathParam("id") int id, InputStream input);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") int id);

    @GET
    @Path("find")
    @Produces("application/x-protobuf")
    Response query(@QueryParam("year") int year, @QueryParam("orderby") String orderBy);
}
