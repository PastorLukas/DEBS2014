package cz.muni.fi.sbapr.debs2014;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.DeploymentException;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.DeploymentResult;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerControlEvent;
import static cz.muni.fi.sbapr.debs2014.csv.CSVInput.*;
import cz.muni.fi.sbapr.debs2014.csv.CSVEventSender;
import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import cz.muni.fi.sbapr.debs2014.io.CsvSensorEventInputAdapter;
import cz.muni.fi.sbapr.debs2014.io.CsvSensorEventInputAdapterSpec;
import cz.muni.fi.sbapr.debs2014.statement.GlobalLoadMedianStatement;
import cz.muni.fi.sbapr.debs2014.statement.RuntimeReportingStatement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.MathContext;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import static java.util.concurrent.TimeUnit.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.espertech.esper.client.deploy.Module;
import com.espertech.esper.client.deploy.ParseException;
import static cz.muni.fi.sbapr.debs2014.annotation.AnnotationProcessor.*;
import cz.muni.fi.sbapr.debs2014.listener.SensorEventListener;
import cz.muni.fi.sbapr.debs2014.subscriber.GlobalLoadAverageSubscriber;
import cz.muni.fi.sbapr.debs2014.subscriber.RuntimeReportingSubscriber;
import cz.muni.fi.sbapr.debs2014.subscriber.SensorEventSubscriber;
import cz.muni.fi.sbapr.debs2014.subscriber.TestSubscriber;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Lukáš Pástor <396308@mail.muni.cz>
 */
public class Query2 {
        
    private static final Logger LOG = LoggerFactory.getLogger(Query2.class);
    private static final String ENGINE_URI = Query2.class.getName();     
    private static long initTime = MILLISECONDS.convert(1377986400L, SECONDS); // Sat, 31 Aug 2013 22:00:00 GMT        
    
        
    private static void start() {
        
        //      Create engine configuration        
        Configuration configuration = new Configuration();        
        //      Register event type
        configuration.addEventType(SensorEvent.class.getSimpleName(), SensorEvent.class);                        
        //      External clocking (default true, false stops internal time advancing)
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false); 
        //      Importing anotations through api
        configuration.addImport("cz.muni.fi.sbapr.debs2014.anotation.*");                          
        
        //      Construct service engine instance and runtime                  
        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, configuration);
                               
        //      Switch to external clocking at runtime
        EPRuntime runtime = epService.getEPRuntime(); 
        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        runtime.sendEvent(new CurrentTimeEvent(initTime));                   
        
        
        
        EPAdministrator administrator = epService.getEPAdministrator();   
        
//        String loadStream = 
//                "insert into LoadStream select * from SensorEvent(property = true)";
//        String lastHourWindow = 
//                "create window LastHourWindow.win:time(5 min) as SensorEvent";                                
//        String populateWindow = 
//                "insert into LastHourWindow select * from LoadStream";                
//        
//        administrator.createEPL(loadStream, "LoadStream");        
//        administrator.createEPL(lastHourWindow, "LastHourWindow");
//        administrator.createEPL(populateWindow, "populateWindow");
        
        /*
        // A non-overlapping context started after 10 seconds 
        // that ends 1 minute after it starts and that again starts 10 seconds thereafter.
        // An overlapping context that starts a new context partition every 5 seconds
        // and each context partition lasts 1 minute
        create context HouseContext
            initiated by distinct(houseId) SensorEvent as newOrder 
            terminated by CloseOrderEvent(closeOrderId = newOrder.orderId)
        */
        
        
//              Reports tick events
//        SensorEventStatement sensorEventStatement = new SensorEventStatement(administrator);
//        sensorEventStatement.setSubscriber(new SensorEventSubscriber());
//        sensorEventStatement.addListener(new SensorEventListener());
        
        
//              Reports engine runtime
        RuntimeReportingStatement runtimeStatement = new RuntimeReportingStatement(administrator);
//        runtimeStatement.setSubscriber(new RuntimeReportingSubscriber());        
//        runtimeStatement.addListener(new RuntimeReportingListener());           
                        
        GlobalLoadMedianStatement globalLoadMedianStatement = new GlobalLoadMedianStatement(administrator);             
        
                   
        CSVEventSender sender = new CSVEventSender();
        sender.startSendingEvents();        
    }
    
    private static void history() {
                
        Configuration configuration = new Configuration();                       
//        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false); 
//        configuration.getEngineDefaults().getExecution().setAllowIsolatedService(true);                     
//        configuration.getEngineDefaults().getViewResources().setShareViews(false);

        configuration.addImport("cz.muni.fi.sbapr.debs2014.listener.*");
        configuration.addImport("cz.muni.fi.sbapr.debs2014.anotation.*");
        configuration.addEventType(SensorEvent.class.getSimpleName(), SensorEvent.class);                                        
        
        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, configuration);                
//        EPServiceProviderIsolated isolatedService = epService.getEPServiceIsolated(ENGINE_URI + "-isolated");
        
        EPRuntime runtime = epService.getEPRuntime();
//        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));                      
        runtime.sendEvent(new CurrentTimeEvent(initTime));
        
//        EPRuntimeIsolated isolatedRuntime = isolatedService.getEPRuntime();
//        isolatedRuntime.sendEvent(new CurrentTimeEvent(initTime));                
                
        EPAdministrator administrator = epService.getEPAdministrator();  
//        EPAdministratorIsolated isolatedAdministrator = isolatedService.getEPAdministrator();
        
//              Reports tick events
//        SensorEventStatement sensorEventStatement = new SensorEventStatement(administrator);
//        sensorEventStatement.setSubscriber(new SensorEventSubscriber());
//        sensorEventStatement.addListener(new SensorEventListener());
                
        
//              Reports engine runtime
        RuntimeReportingStatement runtimeStatement = new RuntimeReportingStatement(administrator);
//        runtimeStatement.setSubscriber(new RuntimeReportingSubscriber());        
//        runtimeStatement.addListener(new RuntimeReportingListener());                                   
        
        
        final File file = new File(PC_E_CSV_DATA + SORTED_100M);
        CsvSensorEventInputAdapterSpec spec = new CsvSensorEventInputAdapterSpec(file);
        CsvSensorEventInputAdapter adapter = new CsvSensorEventInputAdapter(epService, spec);
        
        adapter.start();
        
//        runtimeStatement.stop();        
//        administrator.destroyAllStatements();
    }            
    
    private void statementTest() {       
        Configuration configuration = new Configuration();                
        configuration.addEventType(SensorEvent.class.getSimpleName(), SensorEvent.class);                                
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);         
        configuration.addImport("cz.muni.fi.sbapr.debs2014.anotation.*");                          
        configuration.getEngineDefaults().getExpression().setMathContext(MathContext.DECIMAL32);   
//        configuration.getEngineDefaults().getEventMeta().setDefaultEventRepresentation(Configuration.EventRepresentation.);
//      create schema
        
        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, configuration);
                                       
        EPRuntime runtime = epService.getEPRuntime(); 
        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        runtime.sendEvent(new CurrentTimeEvent(initTime));                   
                        
        EPAdministrator administrator = epService.getEPAdministrator();                       
        
        
//              Reports tick events
//        SensorEventStatement sensorEventStatement = new SensorEventStatement(administrator);
//        sensorEventStatement.setSubscriber(new SensorEventSubscriber());
//        sensorEventStatement.addListener(new SensorEventListener());
        
                
//        runtimeStatement.setSubscriber(new RuntimeReportingSubscriber());        
//        runtimeStatement.addListener(new RuntimeReportingListener());           
                        
//        GlobalLoadMedianStatement globalLoadMedianStatement = new GlobalLoadMedianStatement(administrator);             
        
//        String loadStream = 
//                "insert into LoadStream select * from SensorEvent(property = true)";
//        String lastHourWindow = 
//                "create window LastHourWindow.win:time(5 min) as SensorEvent";                                
//        String populateWindow = 
//                "insert into LastHourWindow select * from LoadStream";                
//        
//        administrator.createEPL(loadStream, "LoadStream");        
//        administrator.createEPL(lastHourWindow, "LastHourWindow");
//        administrator.createEPL(populateWindow, "populateWindow");
        
        //  CONTEXTS
        String nestedContext 
                = "create context NestedContext "
                    + "context SegmentedByHouse partition by houseId from SensorEvent, "
                    + "context SegmentedByHousehold partition by householdId from SensorEvent, "
                    + "context SegmentedByPlug partition by plugId from SensorEvent "
                    ;
//        administrator.createEPL(nestedContext);
        
        
        
        String segmentedByHouseContext
                = "create context SegmentedByHouse partition by houseId from SensorEvent";
//        administrator.createEPL(segmentedByHouseContext);        
        
        
        
        String segmentedByHouseholdContext
                = "create context SegmentedByHousehold partition by houseId and householdId from SensorEvent";
//        administrator.createEPL(segmentedByHouseholdContext);  
        
        
        
        String segmentedByPlugContext
                = "create context SegmentedByPlug partition by houseId and householdId and plugId from SensorEvent";
        administrator.createEPL(segmentedByPlugContext);  
                        		        
        
        
        //  CONTEXT VARIABLES
        String plugCount 
                = "context SegmentedByHouse create variable integer plug_count = 0";
        administrator.createEPL(plugCount);  
        
        String updatePlugCountExpression
                = "context SegmentedByHouse select count(*) as plugCount from SensorEvent.std:unique(householdId, plugId) ";
        
        String plugCountUpdateExpression
                = "create expression updatePlugCount {()}";                
                                
        String a 
                = "context SegmentedByHouse on SensorEvent as e set plug_count = 0;";
                                             
        
//        RuntimeReportingStatement runtimeStatement = new RuntimeReportingStatement(administrator);
//        TestStatement testStatement = new TestStatement(administrator);
                
                
        CSVEventSender sender = new CSVEventSender();
        sender.startSendingEvents();
    }
    
    private void quary2() { 
        Configuration configuration = new Configuration();                
        configuration.addEventType(SensorEvent.class.getSimpleName(), SensorEvent.class);                                
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);         
        configuration.addImport("cz.muni.fi.sbapr.debs2014.anotation.*");                          
        configuration.getEngineDefaults().getExpression().setMathContext(MathContext.DECIMAL32);   

        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, configuration);
                                       
        EPRuntime runtime = epService.getEPRuntime(); 
        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        runtime.sendEvent(new CurrentTimeEvent(initTime));                   
                        
        EPAdministrator administrator = epService.getEPAdministrator(); 
        EPDeploymentAdmin deploymentAdministrator = administrator.getDeploymentAdmin();
                                          
        final String[] resourceName = new String[] {"/query2.epl"};                                                            
        final List<Module> moduleList = loadModules(resourceName);                
        final List<String> deploymentId = addModules(moduleList);
        final List<DeploymentResult> deploymentResults = deployModules(moduleList);
                        
//        Arrays.asList(resourceName).stream().forEach(resource -> deploy(add(load(resource))));         
        administrator.getStatement("RuntimeReportingStatement").setSubscriber(new RuntimeReportingSubscriber());
        administrator.getStatement("SensorEventStream").setSubscriber(new SensorEventSubscriber());
//        administrator.getStatement("TestStream2").setSubscriber(new TestSubscriber()); 
//        administrator.getStatement("TestWindow").setSubscriber(new TestSubscriber()); 
//        administrator.getStatement("LoadStream").setSubscriber(new SensorEventSubscriber());
        administrator.getStatement("TestStatement").setSubscriber(new TestSubscriber());
        
        CSVEventSender sender = new CSVEventSender();
        sender.startSendingEvents();
            
//        Undeploy module, destroying all statements
//        deployAdmin.undeploy(moduleDeploymentId);

//        Remove module; It will no longer be known
//        deployAdmin.remove(moduleDeploymentId);                                  
    }    
    
    private void testingModule() { 
        Configuration configuration = new Configuration();                
        configuration.addEventType(SensorEvent.class.getSimpleName(), SensorEvent.class);                                
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);         
        configuration.addImport("cz.muni.fi.sbapr.debs2014.anotation.*");                       
        MathContext mathContext = new MathContext(6, RoundingMode.HALF_UP);
        mathContext = MathContext.DECIMAL32;
        
        configuration.getEngineDefaults().getExpression().setMathContext(mathContext);   

        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, configuration);
                                       
        EPRuntime runtime = epService.getEPRuntime(); 
        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        runtime.sendEvent(new CurrentTimeEvent(initTime));                   
                        
        EPAdministrator administrator = epService.getEPAdministrator(); 
        EPDeploymentAdmin deploymentAdministrator = administrator.getDeploymentAdmin();
                                          
        final String[] resourceName = new String[] {"/testingModule-00.epl"};                                 
        Arrays.asList(resourceName).stream().forEach(resource -> deploy(add(load(resource))));                               
        
        
        administrator.getStatement("RuntimeStream").setSubscriber(new RuntimeReportingSubscriber());        
//        administrator.getStatement("SensorEventStream").setSubscriber(new SensorEventSubscriber());  
        administrator.getStatement("LoadStream").setSubscriber(new SensorEventSubscriber()); 
                     
        
        administrator.getStatement("LoadAverageStream").setSubscriber(new GlobalLoadAverageSubscriber());
//        administrator.getStatement("OverPlugStatement").setSubscriber(new TestSubscriber());        
//        administrator.getStatement("NewPlugStream").setSubscriber(new TestSubscriber());
//        administrator.getStatement("OverPlugStatement").setSubscriber(new TestSubscriber());
        
        
//        administrator.getStatement("GlobalLoadAverageStatement").setSubscriber(new GlobalLoadAverageSubscriber());        
//        administrator.getStatement("GlobalLoadAverageStatement").setSubscriber(new GlobalLoadAverageSubscriber());
//        administrator.getStatement("PlugLoadAverageStatement").setSubscriber(new TestSubscriber());        
        
        
//        administrator.getStatement("TestStream2").setSubscriber(new TestSubscriber()); 
//        administrator.getStatement("TestWindow").setSubscriber(new TestSubscriber());         
//        administrator.getStatement("TestWindowStatement")
//                .setSubscriber(new TestSubscriber());
//                .addListener(new TestListener());        
                       
        CSVEventSender sender = new CSVEventSender();
        sender.startSendingEvents();        
    }    
    
    private void testingModule1() { 
        Configuration configuration = new Configuration();                
        configuration.addEventType(SensorEvent.class.getSimpleName(), SensorEvent.class);                                
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);         
        configuration.addImport("cz.muni.fi.sbapr.debs2014.anotation.*");                       
        MathContext mathContext = new MathContext(6, RoundingMode.HALF_UP);
        mathContext = MathContext.DECIMAL32;
        
        configuration.getEngineDefaults().getExpression().setMathContext(mathContext);   

        EPServiceProvider epService = EPServiceProviderManager.getProvider(ENGINE_URI, configuration);
                                       
        EPRuntime runtime = epService.getEPRuntime(); 
        runtime.sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        runtime.sendEvent(new CurrentTimeEvent(initTime));                   
                        
        EPAdministrator administrator = epService.getEPAdministrator(); 
        EPDeploymentAdmin deploymentAdministrator = administrator.getDeploymentAdmin();
                                          
        final String[] resourceName = new String[] {"/testingModule-01.epl"};                                 
        Arrays.asList(resourceName).stream().forEach(resource -> deploy(add(load(resource))));                               
        
        
        administrator.getStatement("RuntimeStream").setSubscriber(new RuntimeReportingSubscriber());        
//        administrator.getStatement("SensorEventStream").setSubscriber(new SensorEventSubscriber());
//        administrator.getStatement("SensorEventStream").addListener(new SensorEventListener());               
        administrator.getStatement("LoadStream").setSubscriber(new SensorEventSubscriber()); 
        
//        administrator.getStatement("HourWindowStatement").addListener(new SensorEventListener());    
//        administrator.getStatement("LoadSumStream").setSubscriber(new GlobalLoadAverageSubscriber());        
        
//        administrator.getStatement("LoadAverageStream").setSubscriber(new GlobalLoadAverageSubscriber());
//        administrator.getStatement("OverPlugStatement").setSubscriber(new TestSubscriber());        
//        administrator.getStatement("NewPlugStream").setSubscriber(new TestSubscriber());
//        administrator.getStatement("OverPlugStatement").setSubscriber(new TestSubscriber());
        
        
//        administrator.getStatement("GlobalLoadAverageStatement").setSubscriber(new GlobalLoadAverageSubscriber());        
//        administrator.getStatement("GlobalLoadAverageStatement").setSubscriber(new GlobalLoadAverageSubscriber());
//        administrator.getStatement("PlugLoadAverageStatement").setSubscriber(new TestSubscriber());        
        
        
//        administrator.getStatement("TestStream2").setSubscriber(new TestSubscriber()); 
//        administrator.getStatement("TestWindow").setSubscriber(new TestSubscriber());         
//        administrator.getStatement("TestWindowStatement")
//                .setSubscriber(new TestSubscriber());
//                .addListener(new TestListener());        
                       
        CSVEventSender sender = new CSVEventSender();
        sender.startSendingEvents();        
    }
          
    private void processAnotations(EPAdministrator administrator) {
    
        if (administrator == null)
            throw new NullPointerException("administrator");
                        
        for (String statementName : administrator.getStatementNames()) {
            annotateStatement(administrator.getStatement(statementName));
        }
    }
    
    private List<Module> loadModules(final String[] resourceNames) {
                       
        EPDeploymentAdmin administrator = EPServiceProviderManager.getProvider(ENGINE_URI)
                .getEPAdministrator().getDeploymentAdmin();
        
        if (administrator == null)
            throw new NullPointerException("administrator");
        
        if (resourceNames == null) 
            throw new NullPointerException("resourceUri");
        
        List<Module> modules = new ArrayList<>();     
        try {            
            for (String resourceName : resourceNames) {                
                
                if (resourceName == null ) {                    
                    LOG.trace("can not get resource : resourceName is null");                    
                    continue;
                }
                
                if (resourceName.length() == 0) {
                    LOG.trace("can not get resource : zero length string");
                    continue;
                }
                
                LOG.trace("loading resource : {}", resourceName);
                URL resourceUrl = getClass().getResource(resourceName);                
                modules.add(administrator.read(resourceUrl));
            }            
        } catch (final IOException | ParseException ex) {
            LOG.info(ex.toString());
            modules.clear();      
        }
        LOG.trace("loaded resources : {}", modules.size()); 
        return modules;
    }
    
    private List<String> addModules(final Collection<Module> modules) {
        
        EPDeploymentAdmin administrator = EPServiceProviderManager.getProvider(ENGINE_URI)
                .getEPAdministrator().getDeploymentAdmin();
        
        if (administrator == null)
            throw new NullPointerException("administrator");
        
        if (modules == null)
            throw new NullPointerException("modules");
        
        List<String> deploymentId = new ArrayList<>();        
        for (Module module : modules) {
            
            if (module == null) {
                LOG.trace("cannot add : module is null");
                continue;
            }
            
            LOG.trace("adding module : {}", module.getName());
            deploymentId.add(administrator.add(module));
        }
        LOG.trace("added modules : {}", deploymentId.size());        
        return deploymentId;
    }
    
    private List<DeploymentResult> deployModules(final Collection<Module> modules) {
        
        EPDeploymentAdmin administrator = EPServiceProviderManager.getProvider(ENGINE_URI)
                .getEPAdministrator().getDeploymentAdmin();
        
        if (administrator == null)
            throw new NullPointerException("administrator");
        
        if (modules == null) 
            throw new NullPointerException("modules");
        
        List<DeploymentResult> deployedModules = new ArrayList<>();        
        try {            
            for (Module module : modules) {
                if (module == null) {
                    LOG.trace("cannot deploy : module is null");
                    continue;
                }
                
                final DeploymentOptions options = new DeploymentOptions();
                LOG.trace("deploying module : {}", module.getName());                
                deployedModules.add(administrator.deploy(module, options));                
            }         
        } catch (final DeploymentException ex) {  
            LOG.info(ex.toString());
            deployedModules.clear();
        }       
        LOG.trace("deployed modules : {}", deployedModules.size());
        return deployedModules;
    }
                
    private Module load(final String resourceName) {
        
        EPDeploymentAdmin administrator = EPServiceProviderManager.getProvider(ENGINE_URI)
                .getEPAdministrator().getDeploymentAdmin();
        
        if (administrator == null)
            throw new NullPointerException("administrator");
        
        if (resourceName == null) 
            throw new NullPointerException("resourceName");
        
        Module module = null;                
        try {
            LOG.trace("loading resource : {}", resourceName);
            module = administrator.read(getClass().getResource(resourceName));
        } catch (final IOException | ParseException ex) {
            //LOG.info(ex.toString());
            ex.printStackTrace();
        }
        return module;
    }       
    
    private Module add(final Module module) {
        
        EPDeploymentAdmin administrator = EPServiceProviderManager.getProvider(ENGINE_URI)
                .getEPAdministrator().getDeploymentAdmin();
        
        if (administrator == null)
            throw new NullPointerException("administrator");
        
        if (module == null)
            throw new NullPointerException("module");                                         
        
        LOG.trace("adding module : {}", module.getName());
        administrator.add(module);             
        return module;
    }
    
    private Module deploy(final Module module) {
        
        EPDeploymentAdmin administrator = EPServiceProviderManager.getProvider(ENGINE_URI)
                .getEPAdministrator().getDeploymentAdmin();
        
        if (administrator == null)
            throw new NullPointerException("administrator");
        
        if (module == null)
            throw new NullPointerException("module");
                
        DeploymentResult deployedResult = null;
        try {
            final DeploymentOptions options = new DeploymentOptions();
            
            LOG.trace("deploying module : {}", module.getName());    
            deployedResult = administrator.deploy(module, options);     
        } catch (final DeploymentException ex) {
//            LOG.info(ex.toString());
            ex.printStackTrace();            
        }
        return module;
    }       
    
    
    public String loadScript(String pathname) {        
        return loadScript(new File(pathname));   
    }                    
    
    public String loadScript(URL resourceUrl) throws URISyntaxException {
        try {
            return loadScript(Paths.get(resourceUrl.toURI()));
        } catch (URISyntaxException ex) {
            LOG.info(ex.toString());
        }
        return null;                
    }                
    
    private String loadScript(Path path) {
        return loadScript(path.toFile());
    }
    
    private String loadScript(File file) {
        try {
            return new Scanner(file).useDelimiter("\\Z").next();   
        } catch (FileNotFoundException ex) {
            LOG.info(ex.toString());
        }        
        return null;
    }             
    
    private String getEPL(URL resourceUrl) {
        try {
            return IOUtils.toString(resourceUrl);            
        } catch (NullPointerException | IOException ex) {
            LOG.info(ex.getMessage());
        }
        return null;
    }
    
    private String getELP(Path resourcePath) {
        try {
            return FileUtils.readFileToString(resourcePath.toFile());            
        } catch (NullPointerException | IOException ex) {
            LOG.info(ex.toString());
        }
        return null;
    }                
    
    public static void main(String[] args) throws URISyntaxException, IOException {
                
        Query2 q = new Query2();
//        q.testingModule(); 
        q.testingModule1();
//        q.quary2();
        
//        Query2.start();
//        Query2.history();
//        Query2.statementTest();
        
        
//        URL resourceUrl = q.getClass().getResource("/query2.epl");
//        System.out.println(q.getEPL(resourceUrl));
//        System.out.println(q.loadScript(resourceUrl));      
        
//        URL resourceUrl = getClass().getResource("/contextEPL.epl");        
//        String expression = getEPL(resourceUrl);
//        EPStatementObjectModel model = administrator.compileEPL(expression);                
//        EPStatement s = administrator.create(model, "SegmentedByHouse");   
//        
        
//        out.println(new String(readAllBytes(get("/contextEPL.epl"))));
//        out.println(new String(readAllBytes(get(resourceUrl.toURI()))));
        
//        LOG.info("Name          : {}", Query2.class.getName());
//        LOG.info("CanonicalName : {}", Query2.class.getCanonicalName());        
//        LOG.info("SimpleName    : {}", Query2.class.getSimpleName());
    }       
}
