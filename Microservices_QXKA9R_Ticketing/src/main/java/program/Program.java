package program;

import banking.IBankingService;
import movies.IMovieDatabase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import providers.ProtoBufProvider;
import ticketing.TicketingService;

public class Program {

    public static void main(String[] args) {
        try {
            Swarm swarm = new Swarm(args);
            JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
            deployment.addPackage(TicketingService.class.getPackage());
            deployment.addPackage(ProtoBufProvider.class.getPackage());
            deployment.addPackage(IMovieDatabase.class.getPackage());
            deployment.addPackage(IBankingService.class.getPackage());
            deployment.addAllDependencies();
            swarm.start();
            swarm.deploy(deployment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}