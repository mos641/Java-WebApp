/*
Authors:        abde
File:           Appuser.java
Description:    Definition of the AppUser Entity
*/
package cst.entities;

import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * AppUser entity class
 * @author abde
 */
@Entity
@Table(name = "APPUSER")
@XmlRootElement
// change depending on database definition
@NamedQueries({
    @NamedQuery(name = "Appuser.findAll", query = "SELECT a FROM Appuser a"),
    @NamedQuery(name = "Appuser.findById", query = "SELECT a FROM Appuser a WHERE a.id = :id"),
//    @NamedQuery(name = "Appuser.findById", query = "SELECT a FROM Appuser a WHERE a.id = :userid"),
    @NamedQuery(name = "Appuser.findByGroupname", query = "SELECT a FROM Appuser a WHERE a.groupname = :groupname"),
    @NamedQuery(name = "Appuser.findByPassword", query = "SELECT a FROM Appuser a WHERE a.password = :password")})
public class Appuser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    // change depending on database definition
    @Column(name = "ID")
	// @Column(name = "USERID")
    private String id;
    
    @Size(max = 255)
    @Column(name = "GROUPNAME")
    private String groupname;
    
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Default Constructor
     */
    public Appuser() {
    }

    /**
     * Overloaded constructor to set id
     * @param id The id being set
     */
    public Appuser(String id) {
        this.id = id;
    }

    /**
     * Return user id
     * @return the user id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the user id
     * @param id new user id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Return the user group name
     * @return user group name
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * Set the user group name
     * @param groupname new user group name
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    /**
     * Get the user password, returns null, do not display user passwords
     * @return null
     */
    public String getPassword() {
        return null;
    }

    /**
     * Sets the user password
     * @param password new user password
     */
    public void setPassword(String password) {
        if ((password == null || password == "") && (this.password != null || this.password != "")) {
            return;
        }

        // initialize a PasswordHash object which will generate password hashes
        Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
        PasswordHash passwordHash = instance.get();
        passwordHash.initialize(new HashMap<String, String>()); // todo: are the defaults good enough?

        // now we can generate a password entry for a given password
        String passwordEntry = password; //pretend the user has chosen a password mySecretPassword
        passwordEntry = passwordHash.generate(passwordEntry.toCharArray());
        //at this point, passwordEntry refers to a salted/hashed password entry corresponding to mySecretPassword

        this.password = passwordEntry;
    }

    /**
     * Sets an id hash code
     * @return id hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Checks if two users are equal
     * @param object user to compare to
     * @return true or false whether users are equal
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appuser)) {
            return false;
        }
        Appuser other = (Appuser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string of the user id
     * @return string of user info
     */
    @Override
    public String toString() {
        return "Appuser[ id=" + id + " ]";
    }
    
}
