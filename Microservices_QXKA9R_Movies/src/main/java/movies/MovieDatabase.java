package movies;

import movies.Movies.Movie;
import movies.Movies.MovieId;
import movies.Movies.MovieIdList;
import movies.Movies.MovieList;

import java.util.Map;
import java.util.TreeMap;

public class MovieDatabase implements IMovieDatabase {

    private static final TreeMap<Integer, Movie> movieMap = new TreeMap<>();

    @Override
    public MovieList getAll() {
        return MovieList.newBuilder().addAllMovie(movieMap.values()).build();
    }

    @Override
    public Movie get(int id) {
        return movieMap.get(id);
    }

    @Override
    public MovieId add(Movie movie) {
        int id = (movieMap.size() != 0) ? movieMap.lastKey() + 1 : 0;
        movieMap.put(id, movie);
        return MovieId.newBuilder().setId(id).build();
    }

    @Override
    public void update(int id, Movie movie) {
        movieMap.put(id, movie);
    }

    @Override
    public void delete(int id) {
        movieMap.remove(id);
    }

    @Override
    public MovieIdList query(int year, String orderBy) {
        if (!orderBy.equals("Director") && !orderBy.equals("Title"))
            return null;
        boolean orderMode = orderBy.equals("Director");
        TreeMap<String, Integer> resultMap = new TreeMap<>();
        for (Map.Entry<Integer, Movie> entry : movieMap.entrySet())
            if (entry.getValue().getYear() == year) {
                String resultKey = (orderMode) ? entry.getValue().getDirector() : entry.getValue().getTitle();
                resultMap.put(resultKey, entry.getKey());
            }
        return MovieIdList.newBuilder().addAllId(resultMap.values()).build();
    }
}
