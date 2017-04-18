package banking;

import banking.Banking.ChargeCardRequest;
import banking.Banking.ChargeCardResponse;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

public class BankingService implements IBankingService {

    @Override
    public Response chargeCard(InputStream input) {
        try {
            ChargeCardRequest request = ChargeCardRequest.parseFrom(input);
            boolean success = request.getAmount() > 0 && request.getCardNumber().length() % 2 == 0;
            return Response.ok(ChargeCardResponse.newBuilder().setSuccess(success).build().toByteArray()).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
