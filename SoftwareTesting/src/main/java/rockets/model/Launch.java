package rockets.model;

import org.neo4j.ogm.annotation.CompositeIndex;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
@CompositeIndex(properties = {"launchDate", "launchVehicle", "launchSite", "orbit"}, unique = true)
public class Launch extends Entity {

    public enum LaunchOutcome {
        FAILED, SUCCESSFUL
    }

    @Property(name = "launchDate")
    private LocalDate launchDate;

    @Relationship(type = "PROVIDES", direction = INCOMING)
    private Rocket launchVehicle;

    private LaunchServiceProvider launchServiceProvider;



    @Property(name = "launchSite")
    private String launchSite;

    @Property(name = "orbit")
    private String orbit;

    @Property(name = "function")
    private String function;

   // private BigDecimal price;
    @Property(name = "launchOutcome")
    private LaunchOutcome launchOutcome;

    @Property(name = "price")
    private BigDecimal price;

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }



    public void setLaunchVehicle(Rocket launchVehicle) {
        this.launchVehicle = launchVehicle;
    }


    public Rocket getLaunchVehicle() {
        return launchVehicle;
    }
	
	  public LaunchServiceProvider getLaunchServiceProvider() {
        return launchServiceProvider;
    }

    public void setLaunchServiceProvider(LaunchServiceProvider launchServiceProvider) {
        this.launchServiceProvider = launchServiceProvider;
    }

   


    public String getLaunchSite() {
        return launchSite;
    }


    public String getOrbit() {
        return orbit;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    public String getFunction() {
        return function;
    }

    public LaunchOutcome getLaunchOutcome() {
        return launchOutcome;
    }
	
	public void setFunction(String function) {
        this.function = function;
    }

  
	
	 public BigDecimal getPrice() {
        return price;
    }

    public void setLaunchSite(String launchSite) {
        this.launchSite = launchSite;
    }
	
	 public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLaunchOutcome(LaunchOutcome launchOutcome) {
        this.launchOutcome = launchOutcome;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return Objects.equals(launchDate, launch.launchDate) &&
                Objects.equals(launchVehicle, launch.launchVehicle) &&
                Objects.equals(launchServiceProvider, launch.launchServiceProvider) &&
                Objects.equals(orbit, launch.orbit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchDate, launchVehicle, launchServiceProvider, orbit);
    }
}
