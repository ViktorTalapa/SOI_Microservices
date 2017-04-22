package ticketing;

import banking.Banking.ChargeCardRequest;
import banking.IBankingService;
import movies.IMovieDatabase;
import movies.Movies.MovieIdList;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import ticketing.Ticketing.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TicketingService implements ITicketingService {

    private static final Map<Integer, Integer> reservations = Collections.synchronizedMap(new HashMap<>());

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
        BuyTicketsResponse.Builder responseBuilder = BuyTicketsResponse.newBuilder();
        IMovieDatabase movieService = getMovieService();
        IBankingService bankingService = getBankingService();
        int movieId = request.getMovieId();
        if (movieService.get(movieId) == null)
            responseBuilder.setSuccess(false);
        else {
            ChargeCardRequest cardRequest = ChargeCardRequest.newBuilder()
                    .setCardNumber(request.getCardNumber())
                    .setAmount(10 * request.getCount())
                    .build();
            boolean success = bankingService.chargeCard(cardRequest).getSuccess();
            responseBuilder.setSuccess(success);
            if (success)
                reservations.put(movieId, reservations.getOrDefault(movieId, 0) + request.getCount());
        }
        return responseBuilder.build();
    }

    @Override
    public GetTicketsResponse getTickets(GetTicketsRequest request) {
        GetTicketsResponse.Builder responseBuilder = GetTicketsResponse.newBuilder();
        for (Map.Entry<Integer, Integer> entry : reservations.entrySet())
            responseBuilder.addTicket(Ticket.newBuilder()
                    .setMovieId(entry.getKey())
                    .setCount(entry.getValue())
                    .build());
        return responseBuilder.build();
    }
}
