package program;

import movies.MovieDatabase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class Program {

    public static void main(String[] args) {
        try {
            Swarm swarm = new Swarm(args);
            JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
            deployment.addPackage(MovieDatabase.class.getPackage());
            deployment.addAllDependencies();
            swarm.start();
            swarm.deploy(deployment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


