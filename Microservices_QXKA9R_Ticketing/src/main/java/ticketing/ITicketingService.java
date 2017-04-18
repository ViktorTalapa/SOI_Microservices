package ticketing;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("ticketing")
@Consumes("application/x-protobuf")
@Produces("application/x-protobuf")
public interface ITicketingService {

    @POST
    @Path("GetMovies")
    Response getMovies(InputStream input);

    @POST
    @Path("BuyTickets")
    Response buyTickets(InputStream input);

    @POST
    @Path("GetTickets")
    Response getTickets(InputStream input);
}
