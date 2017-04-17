package movies;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("")
public interface IMovieDatabase {

    @GET
    @Path("movies")
    @Produces("application/x-protobuf")
    Response getAll();

    @GET
    @Path("movies/{id}")
    @Produces("application/x-protobuf")
    Response get(@PathParam("id") int id);

    @POST
    @Path("movies")
    @Consumes("application/x-protobuf")
    @Produces("application/x-protobuf")
    Response add(InputStream input);

    @PUT
    @Path("movies/{id}")
    @Consumes("application/x-protobuf")
    void update(@PathParam("id") int id, InputStream input);

    @DELETE
    @Path("movies/{id}")
    void delete(@PathParam("id") int id);

    @GET
    @Path("movies/find")
    @Produces("application/x-protobuf")
    Response query(@QueryParam("year") int year, @QueryParam("orderby") String orderBy);
}
