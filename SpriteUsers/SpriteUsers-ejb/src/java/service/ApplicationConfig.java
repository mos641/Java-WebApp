/*
Authors:        abde
File:           ApplicationConfig.java
Description:    Configurations for our RESTful interface
*/

package service;

import java.util.Set;
import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.core.Application;


/*
Replace auth method for testing
@FormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage = "/faces/login.xhtml",
        errorPage = "/faces/login.xhtml"))

Replace auth method for default
@BasicAuthenticationMechanismDefinition
*/

/**
 * Auto generated application configuration with our added annotations
 * @author abde
 */
// change depending on database definition
@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "${'java:comp/DefaultDataSource'}",
//    callerQuery = "#{'select password from app.appuser where userid = ?'}",
    callerQuery = "#{'select password from app.appuser where id = ?'}",
    groupsQuery = "select groupname from app.appuser where id = ?",
//    groupsQuery = "select groupname from app.appuser where userid = ?",
    hashAlgorithm = PasswordHash.class,
    priority = 10
    )
@javax.ws.rs.ApplicationPath("api")
@BasicAuthenticationMechanismDefinition
@ApplicationScoped
@Named
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.SpriteFacadeREST.class);
    }
    
}
