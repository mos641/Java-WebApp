/*
Authors:        abde
File:           SpriteController.java
Description:    Named Bean for the sprite JSF pages/presentation layer
 */

package presentation;

import cst.entities.Sprite;
import cst.entities.SpriteFacade;
import presentation.util.JsfUtil;
import presentation.util.PaginationHelper;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Auto-generated controller for sprite JSF pages
 * @author abde
 */
@Named("spriteController")
@SessionScoped
public class SpriteController implements Serializable {

    private Sprite current;
    private DataModel items = null;
    @EJB
    private SpriteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SpriteController() {
    }

    public Sprite getSelected() {
        if (current == null) {
            current = new Sprite();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SpriteFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Sprite) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Sprite();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     * Creates a sprite entity based on the provided values
     * @return a null string if an error occurs or "Create" to continue
     */
    public String create() {
        try {
            if (!validateCoordinates()) {
                return null;
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpriteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Validates that the x and y values provided are compatible with the input panel dimensions
     * @return a Boolean whether they are valid or not
     */
    public Boolean validateCoordinates() {
        Boolean valid = true;
        if (current.getX() > (current.getPanelWidth() - Sprite.SIZE)) {
            JsfUtil.addErrorMessage("X must be " + (current.getPanelWidth() - Sprite.SIZE) + "  or under, less than PanelWidth (" + current.getPanelWidth() + ") minus size of sprite (" + Sprite.SIZE + ")");
            valid = false;
        }
        if (current.getY() > (current.getPanelHeight() - 10)) {
            JsfUtil.addErrorMessage("Y must be " + (current.getPanelHeight() - Sprite.SIZE) + " or under, less than PanelHeight (" + current.getPanelHeight() + ") minus size of sprite (" + Sprite.SIZE + ")");
            valid = false;
        }
        return valid;
    }

    public String prepareEdit() {
        current = (Sprite) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     * Updates a sprite entity with the provided values
     * @return a null string if an error occurs or "View" to continue
     */
    public String update() {
        try {
            if (!validateCoordinates()) {
                return null;
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpriteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Sprite) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpriteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public Sprite getSprite(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Sprite.class)
    public static class SpriteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SpriteController controller = (SpriteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "spriteController");
            return controller.getSprite(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Sprite) {
                Sprite o = (Sprite) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Sprite.class.getName());
            }
        }

    }

}
