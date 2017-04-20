package banking;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("banking")
public interface IBankingService {

    @POST
    @Path("ChargeCard")
    @Consumes("application/x-protobuf")
    @Produces("application/x-protobuf")
    Response chargeCard(InputStream input);

}
