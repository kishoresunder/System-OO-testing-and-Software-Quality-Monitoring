package rockets.mining;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RocketMinerUnitTest {
    Logger logger = LoggerFactory.getLogger(RocketMinerUnitTest.class);

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;

    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "USA"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe ")
        );

        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};

        int[] lspsIndex = new int[]{0, 2, 0, 0, 1, 2, 2, 2, 2, 1};
        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i])));
        }
        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        Launch.LaunchOutcome[] launchOutcome = new Launch.LaunchOutcome[]
                {
                        Launch.LaunchOutcome.FAILED, Launch.LaunchOutcome.FAILED,
                        Launch.LaunchOutcome.FAILED, Launch.LaunchOutcome.SUCCESSFUL,
                        Launch.LaunchOutcome.FAILED, Launch.LaunchOutcome.FAILED,
                        Launch.LaunchOutcome.SUCCESSFUL, Launch.LaunchOutcome.FAILED,
                        Launch.LaunchOutcome.SUCCESSFUL, Launch.LaunchOutcome.SUCCESSFUL
                };

        // month of each launch
        BigDecimal[] prices = new BigDecimal[]{
                new BigDecimal(12.0), new BigDecimal(26.0),
                new BigDecimal(20.0), new BigDecimal(32.0),
                new BigDecimal(9.0), new BigDecimal(87),
                new BigDecimal(76.0), new BigDecimal(99),
                new BigDecimal(78.0), new BigDecimal(56)};

        String[] launchSite = new String[]{"VAFB","TEST1","VAFB","TEST2","VAFB","VAFB","TEST1","TEST2","TEST1","TEST3"};
         // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));
            l.setLaunchServiceProvider(lsps.get(lspsIndex[i]));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite(launchSite[i]);
            l.setOrbit("LEO");
            l.setPrice(prices[i]);
            l.setLaunchOutcome(launchOutcome[i]);
            spy(l);
            return l;
        }).collect(Collectors.toList());
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostRecentLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopWorkhorse(int k)
    {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);

        Map<Rocket,Integer> elementsCount=new HashMap<Rocket,Integer>();
        for(Launch l:sortedLaunches){
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

        List<Rocket> rockets = new ArrayList<Rocket>() ;
        for(Rocket key:elementsCount.keySet()) {
            rockets.add(key);
        }

        List<Rocket> loadedRockets = miner.mostLaunchedRockets(k);
        assertEquals(k, loadedRockets.size());
        assertEquals(rockets.subList(0, k), loadedRockets);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopBestPerformed(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);

        List<LaunchServiceProvider> launchServiceProviders = new ArrayList<LaunchServiceProvider>() ;
        Map<LaunchServiceProvider,Integer> elementsCount=new HashMap<LaunchServiceProvider,Integer>();
        for(Launch l:sortedLaunches){
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

        List<LaunchServiceProvider> loadedLaunchServiceProviders = miner.mostReliableLaunchServiceProviders(k);
            assertEquals(k, loadedLaunchServiceProviders.size());
        assertEquals(launchServiceProviders.subList(0, k), loadedLaunchServiceProviders);

    }



    @ParameterizedTest
    @ValueSource(strings = {"LEO"})
    public void shouldReturnDominantCountry(String orbit) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);

        Map<String,Integer> elementsCount=new HashMap<String,Integer>();
        String dominantCountry = "";
        for(Launch l:sortedLaunches) {
            if (l.getOrbit().endsWith(orbit)) {
                Integer i = elementsCount.get(l.getLaunchVehicle().getCountry());
                if (i == null) {
                    elementsCount.put(l.getLaunchVehicle().getCountry(), 1);
                } else {
                    elementsCount.put(l.getLaunchVehicle().getCountry(), i + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> list = elementsCount.entrySet().stream()
                .sorted((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()))
                .collect(Collectors.toList());

        dominantCountry = list.get(0).getKey();
        String loadedCountry = miner.dominantCountry(orbit);
        assertEquals(dominantCountry, loadedCountry);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostExpensiveLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getPrice().compareTo(b.getPrice()));
        List<Launch> loadedLaunches = miner.mostExpensiveLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopHotShots(int k) {

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        List<LaunchServiceProvider> launchServiceProviders = new ArrayList<LaunchServiceProvider>() ;
        Map<LaunchServiceProvider,Integer> elementsCount=new HashMap<LaunchServiceProvider,Integer>();
        for(Launch l:sortedLaunches){
            if(l.getLaunchDate().getYear() == 2017)
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
        List<LaunchServiceProvider> loadedLaunchServiceProvider = miner.highestRevenueLaunchServiceProviders(k,2017);
        assertEquals(k, loadedLaunchServiceProvider.size());
        assertEquals(launchServiceProviders.subList(0, k), loadedLaunchServiceProvider);
    }



    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void mostLaunchSite(int k) {

        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);

        List<String> launchSites = new ArrayList<String>() ;
        Map<String,Integer> elementsCount=new HashMap<String,Integer>();
        for(Launch l:sortedLaunches)
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


        List<String> loadedLaunchSites = miner.mostLaunchSite(k);
        assertEquals(k, loadedLaunchSites.size());
        assertEquals(launchSites.subList(0, k), loadedLaunchSites);
    }


}