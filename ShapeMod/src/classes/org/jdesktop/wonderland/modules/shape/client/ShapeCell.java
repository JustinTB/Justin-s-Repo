/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */
package org.jdesktop.wonderland.modules.shape.client;

import java.util.logging.Logger;
import org.jdesktop.wonderland.common.cell.CellStatus;
import org.jdesktop.wonderland.common.cell.state.CellClientState;
import org.jdesktop.wonderland.modules.shape.client.jme.cellrenderer.ShapeCellRenderer;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.Cell.RendererType;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellRenderer;
import org.jdesktop.wonderland.client.cell.ChannelComponent;
import org.jdesktop.wonderland.client.cell.ChannelComponent.ComponentMessageReceiver;
import org.jdesktop.wonderland.client.cell.annotation.UsesCellComponent;
import org.jdesktop.wonderland.client.contextmenu.ContextMenuActionListener;
import org.jdesktop.wonderland.client.contextmenu.ContextMenuItem;
import org.jdesktop.wonderland.client.contextmenu.ContextMenuItemEvent;
import org.jdesktop.wonderland.client.contextmenu.SimpleContextMenuItem;
import org.jdesktop.wonderland.client.contextmenu.cell.ContextMenuComponent;
import org.jdesktop.wonderland.client.contextmenu.spi.ContextMenuFactorySPI;
import org.jdesktop.wonderland.client.input.Event;
import org.jdesktop.wonderland.client.input.EventClassListener;
import org.jdesktop.wonderland.client.jme.input.MouseButtonEvent3D;
import org.jdesktop.wonderland.client.jme.input.MouseEvent3D.ButtonId;
import org.jdesktop.wonderland.client.scenemanager.event.ContextEvent;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.messages.CellMessage;
import org.jdesktop.wonderland.modules.shape.common.ShapeCellChangeMessage;
import org.jdesktop.wonderland.modules.shape.common.ShapeCellClientState;

public class ShapeCell extends Cell {
    
    private String shapeType = null;
    private String textureURI = null;
    private ShapeCellRenderer renderer = null;
    private MouseEventListener listener = null;
    private static final Logger logger = Logger.getLogger(ShapeCell.class.getName());
    @UsesCellComponent private ContextMenuComponent contextComp = null;
    private ContextMenuFactorySPI menuFactory = null;

    public ShapeCell(CellID cellID, CellCache cellCache) {
        super(cellID, cellCache);
    }

    @Override
    public void setClientState(CellClientState state) {
        super.setClientState(state);
        this.shapeType = ((ShapeCellClientState)state).getShapeType();
        this.textureURI = ((ShapeCellClientState)state).getTextureURI();
    }

    public String getShapeType() {
        return this.shapeType;
    }

    public String getTextureURI() {
        return textureURI;
    }

    @Override
    public void setStatus(CellStatus status, boolean increasing) {
        super.setStatus(status, increasing);

        if (status == CellStatus.INACTIVE && increasing == false) {
            if (listener != null) {
                listener.removeFromEntity(renderer.getEntity());
                listener = null;
            }

            ChannelComponent channel = getComponent(ChannelComponent.class);
            channel.removeMessageReceiver(ShapeCellChangeMessage.class);

            if (menuFactory != null) {
                contextComp.removeContextMenuFactory(menuFactory);
                menuFactory = null;
            }
        }
        else if (status == CellStatus.RENDERING && increasing == true) {
            if (listener == null) {
                listener = new MouseEventListener();
                listener.addToEntity(renderer.getEntity());
            }
            
            ShapeCellMessageReceiver recv = new ShapeCellMessageReceiver();
            ChannelComponent channel = getComponent(ChannelComponent.class);
            channel.addMessageReceiver(ShapeCellChangeMessage.class, recv);

            if (menuFactory == null) {
                final MenuItemListener l = new MenuItemListener();
                menuFactory = new ContextMenuFactorySPI() {
                    public ContextMenuItem[] getContextMenuItems(ContextEvent event) {
                        return new ContextMenuItem[]{
                                    new SimpleContextMenuItem("Change Shape", l)
                                };
                    }
                };
                contextComp.addContextMenuFactory(menuFactory);
            }
        }
    }

    @Override
    protected CellRenderer createCellRenderer(RendererType rendererType) {
        if (rendererType == RendererType.RENDERER_JME) {
            this.renderer = new ShapeCellRenderer(this);
            return this.renderer;
        }
        else {
            return super.createCellRenderer(rendererType);
        }
    }

    class MenuItemListener implements ContextMenuActionListener {
        public void actionPerformed(ContextMenuItemEvent event) {
            shapeType = (shapeType.equals("BOX") == true) ? "SPHERE" : "BOX";
            renderer.updateShape();
            
            ShapeCellChangeMessage msg = new ShapeCellChangeMessage(getCellID(), shapeType, textureURI);
            sendCellMessage(msg);
        }
    }

    class MouseEventListener extends EventClassListener {
        @Override
        public Class[] eventClassesToConsume() {
            return new Class[]{MouseButtonEvent3D.class};
        }

        @Override
        public void commitEvent(Event event) {
            logger.warning("INSIDE COMMITEVENT!");
            MouseButtonEvent3D mbe = (MouseButtonEvent3D) event;
            
            if(mbe.getButton() == ButtonId.BUTTON1){
                if(mbe.isPressed() == true){
                    logger.warning("INSIDE ISPRESSED!");
                    textureURI = "wla://shape1/java.png";
                    renderer.updateTextureURI();
                    ShapeCellChangeMessage msg = new ShapeCellChangeMessage(getCellID(), shapeType, textureURI);
                    sendCellMessage(msg);
                }
                if(mbe.isReleased() == true){
                    logger.warning("INSIDE ISRELEASED!");
                    textureURI = "wla://shape1/MountainPicture.png";
                    renderer.updateTextureURI();
                    ShapeCellChangeMessage msg = new ShapeCellChangeMessage(getCellID(), shapeType, textureURI);
                    sendCellMessage(msg);
                }
            }
            else if(mbe.getButton() == ButtonId.BUTTON2){
                if(mbe.isPressed() == true){
                    logger.warning("INSIDE B2 ISPRESSED!");
                    shapeType = "SPHERE";
                    renderer.updateShape();
                    ShapeCellChangeMessage msg = new ShapeCellChangeMessage(getCellID(), shapeType, textureURI);
                    sendCellMessage(msg);
                }
                if(mbe.isReleased() == true){
                    logger.warning("INSIDE B2 ISRELEASED!");
                    shapeType = "BOX";
                    renderer.updateShape();
                    ShapeCellChangeMessage msg = new ShapeCellChangeMessage(getCellID(), shapeType, textureURI);
                    sendCellMessage(msg);
                }
            }
            else{
                return;
            }
            /*if (mbe.isClicked() == false || mbe.getButton() != ButtonId.BUTTON1) {
                return;
            }*/
            /*shapeType = (shapeType.equals("BOX") == true) ? "SPHERE" : "BOX";
            renderer.updateShape();*/

            
        }
    }

    class ShapeCellMessageReceiver implements ComponentMessageReceiver {
        public void messageReceived(CellMessage message) {
            ShapeCellChangeMessage sccm = (ShapeCellChangeMessage)message;
            if (sccm.getSenderID().equals(getCellCache().getSession().getID()) == false) {
                shapeType = sccm.getShapeType();
                renderer.updateShape();
            }
        }
    }
}
