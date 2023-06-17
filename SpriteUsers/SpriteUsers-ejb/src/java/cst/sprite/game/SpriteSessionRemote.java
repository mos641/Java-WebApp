/*
Authors:        abde
File:           SpriteSessionRemote.java
Description:    Defines an interface for the sprite remote session
*/

package cst.sprite.game;
import cst.entities.Sprite;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.Remote;

/**
 * Provided sprite session interface
 * @author abde
 */
@Remote
public interface SpriteSessionRemote {
    	List<Sprite> getSpriteList() throws RemoteException;
	void newSprite(MouseEvent e) throws RemoteException;
	int getHeight() throws RemoteException;
	int getWidth() throws RemoteException;
}
