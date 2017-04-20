package banking;

import banking.Banking.ChargeCardRequest;
import banking.Banking.ChargeCardResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("banking")
@Produces({"application/x-protobuf", "application/json"})
@Consumes({"application/x-protobuf", "application/json"})
public interface IBankingService {

    @POST
    @Path("ChargeCard")
    ChargeCardResponse chargeCard(ChargeCardRequest request);

}
