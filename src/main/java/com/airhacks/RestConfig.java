//package service;
//
//import org.glassfish.jersey.server.ResourceConfig;
//
//import javax.inject.Inject;
//import javax.ws.rs.ApplicationPath;
//
//@ApplicationPath("/rest")
//public class RestConfig extends ResourceConfig {
//    public static final String JSON_SERIALIZER = "jersey.config.server.provider.packages";
//    public static final String JACKSON_JSON_SERIALIZER = "com.fasterxml.jackson.jaxrs.json;service";
//
//
//    @Inject
//    public RestConfig(){
//        packages(true, "spotitube.service");
//        property(JSON_SERIALIZER, JACKSON_JSON_SERIALIZER);
//    }
//
//
//}
