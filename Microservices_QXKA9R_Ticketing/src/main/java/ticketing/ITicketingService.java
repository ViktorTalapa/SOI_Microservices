package ticketing;

import ticketing.Ticketing.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ticketing")
@Produces({"application/x-protobuf", "application/json"})
@Consumes({"application/x-protobuf", "application/json"})
public interface ITicketingService {

    @POST
    @Path("GetMovies")
    GetMoviesResponse getMovies(GetMoviesRequest request);

    @POST
    @Path("BuyTickets")
    BuyTicketsResponse buyTickets(BuyTicketsRequest request);

    @POST
    @Path("GetTickets")
    GetTicketsResponse getTickets(GetTicketsRequest request);
}
