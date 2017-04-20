package ticketing;

import banking.IBankingService;
import movies.IMovieDatabase;
import movies.Movies.*;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import ticketing.Ticketing.*;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class TicketingService implements ITicketingService {

    private static final String moviesAdress = System.getProperty("microservices.movies.url");
    private static final String bankingAddress = System.getProperty("microservices.banking.url");

    private ResteasyClient getResteasyClient() {
        return new ResteasyClientBuilder().providerFactory(ResteasyProviderFactory.getInstance()).build();
    }

    private IMovieDatabase getMovieService() {
        return getResteasyClient().target(moviesAdress).proxy(IMovieDatabase.class);
    }

    private IBankingService getBankingService() {
        return getResteasyClient().target(bankingAddress).proxy(IBankingService.class);
    }

    @Override
    public Response getMovies(InputStream input) {
        try {
            GetMoviesRequest request = GetMoviesRequest.parseFrom(input);
            IMovieDatabase movieService = getMovieService();

            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @Override
    public Response buyTickets(InputStream input) {
        //Todo
        return null;
    }

    @Override
    public Response getTickets(InputStream input) {
        //Todo
        return null;
    }
}
