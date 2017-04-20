package movies;

import movies.Movies.Movie;
import movies.Movies.MovieId;
import movies.Movies.MovieIdList;
import movies.Movies.MovieList;

import javax.ws.rs.*;

@Path("movies")
@Produces({"application/x-protobuf", "application/json"})
@Consumes({"application/x-protobuf", "application/json"})
public interface IMovieDatabase {

    @GET
    MovieList getAll();

    @GET
    @Path("{id}")
    Movie get(@PathParam("id") int id);

    @POST
    MovieId add(Movie movie);

    @PUT
    @Path("{id}")
    void update(@PathParam("id") int id, Movie movie);

    @DELETE
    @Path("{id}")
    void delete(@PathParam("id") int id);

    @GET
    @Path("find")
    MovieIdList query(@QueryParam("year") int year, @QueryParam("orderby") String orderBy);
}
