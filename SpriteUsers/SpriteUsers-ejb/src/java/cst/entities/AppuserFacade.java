/*
Authors:        abde
File:           SpriteFacade.java
Description:    Abstracting use of our appuser entity with implementing abstract facade
*/
package cst.entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Auto generated AppuserFacade class
 * @author abde
 */
@Stateless
public class AppuserFacade extends AbstractFacade<Appuser> {

    @PersistenceContext(unitName = "SpriteUsersPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AppuserFacade() {
        super(Appuser.class);
    }
    
}
