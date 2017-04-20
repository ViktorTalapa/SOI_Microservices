package movies;

import movies.Movies.Movie;
import movies.Movies.MovieId;
import movies.Movies.MovieIdList;
import movies.Movies.MovieList;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class MovieDatabase implements IMovieDatabase {

    private static final TreeMap<Integer, Movie> movieMap = new TreeMap<>();

    @Override
    public MovieList getAll() {
        return MovieList.newBuilder().addAllMovie(movieMap.values()).build();
    }

    @Override
    public Response get(int id) {
        return Response.ok(movieMap.get(id).toByteArray()).build();
    }

    @Override
    public Response add(InputStream input) {
        try {
            int id = (movieMap.size() != 0) ? movieMap.lastKey() + 1 : 0;
            movieMap.put(id, Movie.parseFrom(input));
            return Response.ok(MovieId.newBuilder().setId(id).build().toByteArray()).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Override
    public void update(int id, InputStream input) {
        try {
            movieMap.put(id, Movie.parseFrom(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        movieMap.remove(id);
    }

    @Override
    public Response query(int year, String orderBy) {
        if (!orderBy.equals("Director") && !orderBy.equals("Title"))
            return Response.serverError().build();
        boolean orderMode = orderBy.equals("Director");

        TreeMap<String, Integer> resultMap = new TreeMap<>();
        for (Map.Entry<Integer, Movie> entry : movieMap.entrySet())
            if (entry.getValue().getYear() == year) {
                String resultKey = (orderMode) ? entry.getValue().getDirector() : entry.getValue().getTitle();
                resultMap.put(resultKey, entry.getKey());
            }
        return Response.ok(MovieIdList.newBuilder().addAllId(resultMap.values()).build().toByteArray()).build();
    }
}
