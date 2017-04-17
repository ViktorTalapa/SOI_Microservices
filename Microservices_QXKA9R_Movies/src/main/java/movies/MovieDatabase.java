package movies;

import javax.ws.rs.core.Response;

import movies.Movies.*;

import java.io.InputStream;

public class MovieDatabase implements IMovieDatabase {

    @Override
    public Response getAll() {
        return null;
    }

    @Override
    public Response get(int id) {
        return null;
    }

    @Override
    public Response add(InputStream input) {
        return null;
    }

    @Override
    public void update(int id, InputStream input) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Response query(int year, String orderBy) {
        return null;
    }
}
