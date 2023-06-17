/*
Authors:        abde
File:           SpriteFacadeREST.java
Description:    Routes and handles all the http REST requests implementing AbstractFacade interface
 */

package service;

import cst.entities.Sprite;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import cst.entities.AbstractFacade;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

/**
 * Defines our RESTful API endpoints
 * @author abde
 */
@javax.ejb.Stateless
@javax.ws.rs.Path("sprites")
@DeclareRoles({"Admin", "RestGroup"})
public class SpriteFacadeREST extends AbstractFacade<Sprite> {

    @PersistenceContext(unitName = "SpriteUsersPU")
    private EntityManager em;

    public SpriteFacadeREST() {
        super(Sprite.class);
    }

    /**
     * POST on root resource Without an id - Creates a new sprite without an id
     * provided With an id - Checks there is a sprite to update, non-existent id
     * returns error
     *
     * @param entity Sprite entity provided sprite values
     * @return response code with requested information
     */
    @javax.ws.rs.POST
    @javax.ws.rs.Consumes({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response createUpdate(Sprite entity) {
        // check if id exists
        Long id = entity.getId();
        if (id == null) {
            // verify the attributes
            String verification = validateSprite(entity, false, null);
            if (verification.compareTo("") == 0) {
                // create new sprite
                super.create(entity);
                return Response.status(Response.Status.CREATED).entity("New sprite created").build();
            } else {
                // invalid attribute(s)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(verification).build();
            }
        } else {
            // check if id exists
            Sprite sprite = findId(id);
            if (sprite == null) {
                // error if no sprite with this ID exists
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Sprite ID cannot be manually set").build();
            } else {
                // verify the attributes
                String verification = validateSprite(entity, true, sprite);
                if (verification.compareTo("") == 0) {
                    // update existing sprite
                    updateSprite(entity, sprite);
                    return Response.ok("Sprite with id " + id + " updated").build();
                } else {
                    // invalid attribute(s)
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(verification).build();
                }
            }
        }
    }

    /**
     * PUT on root resource Unsupported - return error
     *
     * @param entity Sprite entity provided sprite values
     * @return response code with requested information
     */
    @javax.ws.rs.PUT
    @javax.ws.rs.Consumes({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response invCreate(Sprite entity) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).entity("Can not PUT on the root resource").build();
    }

    /**
     * PUT request with an id Replaces a sprite with the matching id
     *
     * @param id provided sprite id
     * @param entity sprite entity provided sprite values
     * @return response code with requested information
     */
    @javax.ws.rs.PUT
    @javax.ws.rs.Path("{id}")
    @javax.ws.rs.Consumes({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response replace(@javax.ws.rs.PathParam("id") Long id, Sprite entity) {
        // make sure IDs are not contradictory
        if (entity.getId() != null && !Objects.equals(entity.getId(), id)){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Body ID " + entity.getId() + " must be null or match provided ID " + id).build();
        }
        
        // check if id exists
        Sprite sprite = findId(id);
        if (sprite == null) {
            // error if no sprite with this ID exists
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Sprite with ID " + id + " cannot be found").build();
        } else {
            // verify the attributes
            String verification = validateSprite(entity, false, null);
            if (verification.compareTo("") == 0) {
                // replace the sprite
                entity.setId(id);
                super.edit(entity);
                return Response.status(Response.Status.OK).entity("Sprite with id " + id + " replaced").build();
            } else {
                // invalid attribute(s)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(verification).build();
            }
        }
    }

    /**
     * POST request with an id Updates a sprite with the matching id with the
     * non-null values
     *
     * @param id provided sprite id
     * @param entity sprite entity provided sprite values
     * @return response code with requested information
     */
    @javax.ws.rs.POST
    @javax.ws.rs.Path("{id}")
    @javax.ws.rs.Consumes({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response update(@javax.ws.rs.PathParam("id") Long id, Sprite entity) {
        // make sure IDs are not contradictory
        if (entity.getId() != null && !Objects.equals(entity.getId(), id)){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Body ID " + entity.getId() + " must be null or match provided ID " + id).build();
        }
        
        // check if id exists
        Sprite sprite = findId(id);
        if (sprite == null) {
            // error if no sprite with this ID exists
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Sprite with ID " + id + " cannot be found").build();
        } else {
            // verify the attributes
            String verification = validateSprite(entity, true, sprite);
            if (verification.compareTo("") == 0) {
                // update existing sprite
                updateSprite(entity, sprite);
                return Response.ok("Sprite with id " + id + " updated").build();
            } else {
                // invalid attribute(s)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(verification).build();
            }
        }
    }

    /**
     * DELETE with an id Delete the selected sprite if it exists
     *
     * @param id provided sprite id
     * @return response code with requested information
     */
    @javax.ws.rs.DELETE
    @javax.ws.rs.Path("{id}")
    @RolesAllowed({"Admin", "RestGroup"})
    public Response remove(@javax.ws.rs.PathParam("id") Long id) {
        // check if id exists
        Sprite sprite = findId(id);
        if (sprite == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Sprite with ID " + id + " cannot be found").build();
        } else {
            super.remove(super.find(id));
            return Response.status(Response.Status.OK).entity("Sprite with id " + id + " removed").build();
        }
    }

    /**
     * GET with an id Returns found sprite if any
     *
     * @param id provided sprite id
     * @return response code with requested information
     */
    @javax.ws.rs.GET
    @javax.ws.rs.Path("{id}")
    @javax.ws.rs.Produces({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response find(@javax.ws.rs.PathParam("id") Long id) {
        // check if id exists
        Sprite sprite = findId(id);
        if (sprite == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("Sprite with ID " + id + " does not exist").build();
        } else {
            return Response.status(Response.Status.OK).entity(sprite).build();
        }
    }

    /**
     * GET root resource Returns all sprites if any
     *
     * @return response code with requested information
     */
    @javax.ws.rs.GET
    @javax.ws.rs.Produces({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response getAll() {
        List<Sprite> sprites = super.findAll();
        if (sprites.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("No sprites exist").build();
        } else {
            return Response.status(Response.Status.OK).entity(sprites).build();
        }
    }

    /**
     * GET with id range Returns the sprites with applicable ids if any
     *
     * @param from beginning id range
     * @param to end id range
     * @return response code with requested information
     */
    @javax.ws.rs.GET
    @javax.ws.rs.Path("{from}/{to}")
    @javax.ws.rs.Produces({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response findRange(@javax.ws.rs.PathParam("from") Integer from, @javax.ws.rs.PathParam("to") Integer to) {
        //return super.findRange(new int[]{from, to});
        List<Sprite> sprites = super.findRange(new int[]{from, to});
        if (sprites.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("No sprites exist").build();
        } else {
            return Response.status(Response.Status.OK).entity(sprites).build();
        }
    }

    /**
     * GET with count Returns the how many sprites there are
     *
     * @return response code with requested information
     */
    @javax.ws.rs.GET
    @javax.ws.rs.Path("count")
    @javax.ws.rs.Produces({javax.ws.rs.core.MediaType.APPLICATION_XML, javax.ws.rs.core.MediaType.APPLICATION_JSON, javax.ws.rs.core.MediaType.TEXT_PLAIN})
    @RolesAllowed({"Admin", "RestGroup"})
    public Response countREST() {
        return Response.status(Response.Status.OK).entity(super.count()).build();
    }

    /**
     * Returns an entity manager
     *
     * @return entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
        // return Response.ok(em).build();
    }

    /**
     * Validates the sprites attributes and returns a string of errors if any
     *
     * @param sprite the sprite to validate
     * @param Boolean whether we are validating for an update or a replace
     * @param ogSprite sprite being updated for extra validation
     * @return string of errors if any
     */
    private String validateSprite(Sprite sprite, Boolean update, Sprite ogSprite) {
        String response = "";
        int maxSpeed = Sprite.MAX_SPEED;
        int size = Sprite.SIZE;
        List<String> invalidVals = new ArrayList<>();
        
        if (((update && sprite.getPanelWidth() != null) || !update) && (sprite.getPanelWidth() == null || sprite.getPanelWidth() <= size)) {
            invalidVals.add(String.format("PanelWidth (must be whole number greater than sprite size of %d)", size));
        }
        
        if (((update && sprite.getPanelHeight() != null) || !update) && (sprite.getPanelHeight() == null || sprite.getPanelHeight() <= size)) {
            invalidVals.add(String.format("PanelHeight (must be whole number greater than sprite size of %d)", size));
        }
        
        if ((!update) && (sprite.getX() == null || sprite.getX() < 0 || sprite.getX() > (sprite.getPanelWidth() - size))) {
            invalidVals.add(String.format("X (must be %d or under, less than PanelWidth (%d) minus size of sprite (%d))", (sprite.getPanelHeight() - size),sprite.getPanelHeight(), size));
        }

        if ((!update) && (sprite.getY() == null || sprite.getY() < 0 || sprite.getY() > (sprite.getPanelHeight() - size))) {
            invalidVals.add(String.format("Y (must be %d or under, less than PanelHeight (%d) minus size of sprite (%d))", (sprite.getPanelHeight() - size), sprite.getPanelHeight(), size));
        }
        
        // validate x and y coordinates for updates
        if (update){
            Integer panelWidth = sprite.getPanelWidth();
            Integer x = sprite.getX();
            if (panelWidth == null){
                panelWidth = ogSprite.getPanelWidth();
            }
            if (x == null){
                x = ogSprite.getX();
            }
            if (x < 0 || x > (panelWidth - size)) {
                invalidVals.add(String.format("X (must be %d or under, less than PanelWidth (%d) minus size of sprite (%d))", (panelWidth - size), panelWidth, size));
            }
            
            Integer panelHeight = sprite.getPanelHeight();
            Integer y = sprite.getY();
            if (panelHeight == null){
                panelHeight = ogSprite.getPanelHeight();
            }
            if (y == null){
                y = ogSprite.getY();
            }
            if (y < 0 || y > (panelHeight - size)) {
                invalidVals.add(String.format("Y (must be %d or under, less than PanelHeight (%d) minus size of sprite (%d))", (panelHeight - size), panelHeight, size));
            }
        }


        if (((update && sprite.getDx() != null) || !update) && (sprite.getDx() == null || sprite.getDx() < -1 * (maxSpeed) || sprite.getDx() > maxSpeed)) {
            invalidVals.add(String.format("dX (must be whole number between -%d to %d)", maxSpeed, maxSpeed));
        }

        if (((update && sprite.getDy() != null) || !update) && (sprite.getDy() == null || sprite.getDy() < -1 * (maxSpeed) || sprite.getDy() > maxSpeed)) {
            invalidVals.add(String.format("dY (must be whole number between -%d to %d)", maxSpeed, maxSpeed));
        }

        for (int i = 0; i < invalidVals.size(); i++) {
            response += invalidVals.get(i);
            if (i < invalidVals.size()) {
                response += ", ";
            }
        }

        return response;
    }

    /**
     * Updates a sprite with received non-null values
     *
     * @param entity the updated values
     * @param sprite the original sprite
     */
    private void updateSprite(Sprite entity, Sprite sprite) {
        Color color =  entity.getColor();
        
        if (color != null) {
            sprite.setColor(color);
        }
        
        if (entity.getX() != null) {
            sprite.setX(entity.getX());
        }

        if (entity.getY() != null) {
            sprite.setY(entity.getY());
        }

        if (entity.getDx() != null) {
            sprite.setDx(entity.getDx());
        }

        if (entity.getDy() != null) {
            sprite.setDy(entity.getDy());
        }
        
        if (entity.getPanelWidth() != null) {
            sprite.setPanelWidth(entity.getPanelWidth());
        }

        if (entity.getPanelHeight() != null) {
            sprite.setPanelHeight(entity.getPanelHeight());
        }

        super.edit(sprite);
    }

    /**
     * Find the sprite with a specified id or null
     *
     * @param id provided sprite id
     * @return the sprite with the selected id or null
     */
    private Sprite findId(Long id) {
        List<Sprite> sprites = super.findAll();
        Sprite found = null;
        for (Sprite sprite : sprites) {
            if (Objects.equals(sprite.getId(), id)) {
                found = sprite;
                return found;
            }
        }
        return found;
    }

}
