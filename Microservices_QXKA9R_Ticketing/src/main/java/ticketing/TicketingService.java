package ticketing;

import banking.IBankingService;
import movies.IMovieDatabase;
import movies.Movies.MovieIdList;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import ticketing.Ticketing.*;

public class TicketingService implements ITicketingService {

    private static ResteasyClient getResteasyClient() {
        return new ResteasyClientBuilder().providerFactory(ResteasyProviderFactory.getInstance()).build();
    }

    private static IMovieDatabase getMovieService() {
        return getResteasyClient().target(System.getProperty("microservices.movies.url")).proxy(IMovieDatabase.class);
    }

    private static IBankingService getBankingService() {
        return getResteasyClient().target(System.getProperty("microservices.banking.url")).proxy(IBankingService.class);
    }

    @Override
    public GetMoviesResponse getMovies(GetMoviesRequest request) {
        GetMoviesResponse.Builder responseBuilder = GetMoviesResponse.newBuilder();
        IMovieDatabase movieService = getMovieService();
        MovieIdList movieIdList = movieService.query(request.getYear(), "Title");
        for (int id : movieIdList.getIdList())
            responseBuilder.addMovie(Ticketing.Movie.newBuilder()
                    .setId(id)
                    .setTitle(movieService.get(id).getTitle())
                    .build());
        return responseBuilder.build();
    }

    @Override
    public BuyTicketsResponse buyTickets(BuyTicketsRequest request) {
        return null;
    }

    @Override
    public GetTicketsResponse getTickets(GetTicketsRequest request) {
        return null;
    }
}
