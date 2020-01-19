package rockets.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class RocketMiner {
    private static Logger logger = LoggerFactory.getLogger(RocketMiner.class);

    private DAO dao;

    public RocketMiner(DAO dao) {
        this.dao = dao;
    }

    /**
     * TODO: to be implemented & tested!
     * Returns the top-k active rocket, as measured by number of launches.
     *
     * @param k the number of rockets to be returned.
     * @return the list of k most active rockets.
     */
    public List<Rocket> mostLaunchedRockets(int k) {
        logger.info("find top " + k + " active launch rockets");
        Collection<Launch> launches = dao.loadAll(Launch.class);

        Map<Rocket,Integer> elementsCount=new HashMap<Rocket,Integer>();
        for(Launch l:launches){
            Integer i = elementsCount.get(l.getLaunchVehicle());
            if(i == null){
                elementsCount.put(l.getLaunchVehicle(),1);
            }
            else{
                elementsCount.put(l.getLaunchVehicle(), i+1);
            }
        }
        List<Map.Entry<Rocket,Integer>>list = elementsCount.entrySet().stream()
                .sorted((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()))
                .collect(Collectors.toList());

        List<Rocket> rockets1 = new ArrayList<Rocket>() ;

        for(Rocket key:elementsCount.keySet()) {
            rockets1.add(key);
        }

        return rockets1.stream().limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most reliable launch service providers as measured
     * by percentage of successful launches.
     *
     * @param k the number of launch service providers to be returned.
     * @return the list of k most reliable ones.
     */
    public List<LaunchServiceProvider> mostReliableLaunchServiceProviders(int k) {
        logger.info("find top " + k + " reliable service providers");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<LaunchServiceProvider> launchServiceProviders = new ArrayList<LaunchServiceProvider>() ;
        Map<LaunchServiceProvider,Integer> elementsCount=new HashMap<LaunchServiceProvider,Integer>();
        for(Launch l:launches){
            if(l.getLaunchOutcome() == Launch.LaunchOutcome.SUCCESSFUL)
            {
                Integer i = elementsCount.get(l.getLaunchServiceProvider());
                if(i == null){
                    elementsCount.put(l.getLaunchServiceProvider(),1);
                }
                else{
                    elementsCount.put(l.getLaunchServiceProvider(), i+1);
                }
            }
        }

        List<Map.Entry<LaunchServiceProvider,Integer>>list = elementsCount.entrySet().stream()
                .sorted((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()))
                .collect(Collectors.toList());


        for(LaunchServiceProvider key:elementsCount.keySet()) {
            launchServiceProviders.add(key);
        }
        return launchServiceProviders.stream().limit(k).collect(Collectors.toList());
    }

    /**
     * <p>
     * Returns the top-k most recent launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most recent launches.
     */
    public List<Launch> mostRecentLaunches(int k) {
        logger.info("find most recent " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchDateComparator = (a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate());
        return launches.stream().sorted(launchDateComparator).limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the dominant country who has the most launched rockets in an orbit.
     *
     * @param orbit the orbit
     * @return the country who sends the most payload to the orbit
     */
    public String dominantCountry(String orbit) {
        logger.info("find dominant Country");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Map<String,Integer> elementsCount=new HashMap<String,Integer>();
        String dominantCountry = "";
        for(Launch l:launches){
            if(l.getOrbit().endsWith(orbit))
            {
                Integer i = elementsCount.get(l.getLaunchVehicle().getCountry());
                if(i == null){
                    elementsCount.put(l.getLaunchVehicle().getCountry(),1);
                }
                else{
                    elementsCount.put(l.getLaunchVehicle().getCountry(), i+1);
                }
            }
        }

        List<Map.Entry<String,Integer>>list = elementsCount.entrySet().stream()
                .sorted((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()))
                .collect(Collectors.toList());

        dominantCountry = list.get(0).getKey();
        return dominantCountry;
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most expensive launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most expensive launches.
     */
    public List<Launch> mostExpensiveLaunches(int k) {
        logger.info("find most expensive top " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchDateComparator = (a, b) -> -a.getPrice().compareTo(b.getPrice());
        return launches.stream().sorted(launchDateComparator).limit(k).collect(Collectors.toList());

    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns a list of launch service provider that has the top-k highest
     * sales revenue in a year.
     *
     * @param k the number of launch service provider.
     * @param year the year in request
     * @return the list of k launch service providers who has the highest sales revenue.
     */
    public List<LaunchServiceProvider> highestRevenueLaunchServiceProviders(int k, int year) {

        logger.info("find top " + k + " Launch Service Providers");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<LaunchServiceProvider> launchServiceProviders = new ArrayList<LaunchServiceProvider>() ;
        Map<LaunchServiceProvider,Integer> elementsCount=new HashMap<LaunchServiceProvider,Integer>();
        for(Launch l:launches){
            if(l.getLaunchDate().getYear() == year)
            {
                Integer i = elementsCount.get(l.getLaunchServiceProvider());
                if(i == null){
                    elementsCount.put(l.getLaunchServiceProvider(),1);
                }
                else{
                    elementsCount.put(l.getLaunchServiceProvider(), i+1);
                }
            }
        }

        List<Map.Entry<LaunchServiceProvider,Integer>>list = elementsCount.entrySet().stream()
                .sorted((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()))
                .collect(Collectors.toList());


        for(LaunchServiceProvider key:elementsCount.keySet()) {
            launchServiceProviders.add(key);
        }
        return launchServiceProviders.stream().limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns a list of the top-k launch sites
     *
     * @param k the number of launch sites.
     * @return a list of the top-k launch sites in completed launches
     */
    public List<String> mostLaunchSite(int k)
    {
        logger.info("find top " + k + " Launch sites");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<String> launchSites = new ArrayList<String>() ;
        Map<String,Integer> elementsCount=new HashMap<String,Integer>();
        for(Launch l:launches)
        {
            Integer i = elementsCount.get(l.getLaunchSite());
            if(i == null){
                elementsCount.put(l.getLaunchSite(),1);
            }
            else{
                elementsCount.put(l.getLaunchSite(), i+1);
            }
        }

        List<Map.Entry<String,Integer>>list = elementsCount.entrySet().stream()
                .sorted((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()))
                .collect(Collectors.toList());


        for(String key:elementsCount.keySet()) {
            launchSites.add(key);
        }
        return launchSites.stream().limit(k).collect(Collectors.toList());
    }
	
	  /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the successful launch rate in <code>year</code> measured by the
     * number of successful launches and total number of launches
     *
     * @param year the year
     * @return the successful launch rate in BigDecimal with scale 2.
     */
    public BigDecimal successfulLaunchRateInYear(int year) {
        return BigDecimal.valueOf(0);
    }

}
