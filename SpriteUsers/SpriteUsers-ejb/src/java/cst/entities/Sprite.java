/*
Authors:        abde
File:           Sprite.java
Description:    Defines our "sprite" entities
*/
package cst.entities;

import utility.ColorAdapter;
import utility.JsonColorSerializer;
import utility.JsonColorDeserializer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;
import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Sprite entity class
 * @author abde
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Sprite implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static Random random = new Random();

    public final static int SIZE = 10;
    public final static int MAX_SPEED = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column
    private Integer panelWidth;
    @Column
    private Integer panelHeight;
    @Column
    private Integer x;
    @Column
    private Integer y;
    @Column
    private Integer dx;
    @Column
    private Integer dy;
    
    @XmlElement
    @XmlJavaTypeAdapter(ColorAdapter.class)
    @JsonbTypeDeserializer(JsonColorDeserializer.class)
    @JsonbTypeSerializer(JsonColorSerializer.class)
    @Column
    private Color color = Color.BLUE;

    /**
     * Default Constructor
     */
    public Sprite() {
    }

    /**
     * Overloaded constructor to set panel height and width
     * @param height the panel height
     * @param width the panel width
     */
    public Sprite(int height, int width) {
        this.panelWidth = width;
        this.panelHeight = height;
        x = random.nextInt(width);
        y = random.nextInt(height);
        dx = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
        dy = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
    }

    /**
     * Overloaded constructor to set panel height, panel width and color
     * @param height the panel height
     * @param width the panel width
     * @param color the color
     */
    public Sprite(int height, int width, Color color) {
        this(height, width);
        this.color = color;
    }

    /**
     * Get the panel width
     * @return the panel width
     */
    public Integer getPanelWidth() {
        return panelWidth;
    }

    /**
     * Set the panel width
     * @param panelWidth new panel width
     */
    public void setPanelWidth(Integer panelWidth) {
        this.panelWidth = panelWidth;
    }

    /**
     * Get the panel height
     * @return the panel height
     */
    public Integer getPanelHeight() {
        return panelHeight;
    }

    /**
     * Set the panel height
     * @param panelHeight new panel height
     */
    public void setPanelHeight(Integer panelHeight) {
        this.panelHeight = panelHeight;
    }

    /**
     * Get the x position
     * @return the x position
     */
    public Integer getX() {
        return x;
    }

    /**
     * Set the x position
     * @param x new x position
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Get the y position
     * @return the y position
     */
    public Integer getY() {
        return y;
    }

    /**
     * Set the y position
     * @param y new y position
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Get the dx or horizontal speed
     * @return the dx speed
     */
    public Integer getDx() {
        return dx;
    }

    /**
     * Set the dx or horizontal speed
     * @param dx new dx speed
     */
    public void setDx(Integer dx) {
        this.dx = dx;
    }

    /**
     * Get the dy or vertical speed
     * @return the dy speed
     */
    public Integer getDy() {
        return dy;
    }

    /**
     * Set the dy or vertical speed
     * @param dy new dy speed
     */
    public void setDy(Integer dy) {
        this.dy = dy;
    }

    /**
     * Get the color
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the color
     * @param color new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Set the graphics
     * @param g the graphics object
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, SIZE, SIZE);
    }

    /**
     * Move the sprite based on position, speed, and panel dimensions
     */
    public void move() {

        // check for bounce and make the ball bounce if necessary
        //
        if (x < 0 && dx < 0) {
            //bounce off the left wall 
            x = 0;
            dx = -dx;
        }
        if (y < 0 && dy < 0) {
            //bounce off the top wall
            y = 0;
            dy = -dy;
        }
        if (x > panelWidth - SIZE && dx > 0) {
            //bounce off the right wall
            x = panelWidth - SIZE;
            dx = -dx;
        }
        if (y > panelHeight - SIZE && dy > 0) {
            //bounce off the bottom wall
            y = panelHeight - SIZE;
            dy = -dy;
        }

        //make the ball move
        x += dx;
        y += dy;
    }
    
    /**
     * Get the id hash code
     * @return id hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compare if two sprites are equal
     * @param object the sprite to compare it to
     * @return true or false if they are equal
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprite)) {
            return false;
        }
        Sprite other = (Sprite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a string of the sprite id
     * @return string of sprite info
     */
    @Override
    public String toString() {
        return "Sprite[ id=" + id + " ]";
    }

}
