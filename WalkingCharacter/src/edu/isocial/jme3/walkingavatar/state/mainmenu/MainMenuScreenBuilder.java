/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.state.mainmenu;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.ScreenController;
import edu.isocial.jme3.walkingavatar.state.login.LoginScreenBuilder;

/**
 *
 * @author justin
 */
public enum MainMenuScreenBuilder {

    MAINMENUBUILDER;

    public static MainMenuScreenBuilder getInstance() {
        return MAINMENUBUILDER;
    }

    public void buildMainMenuScreen(final Nifty nifty, final ScreenController controller) {


        //<editor-fold defaultstate="collapsed" desc="MAIN MENU">
        nifty.addScreen("MainMenu", new ScreenBuilder("MainMenu") {
            {
                controller(controller);
                //<editor-fold defaultstate="collapsed" desc="BACKGROUND">
                layer(new LayerBuilder("background") {
                    {
                        childLayoutVertical();
                     
                        
                        backgroundColor("#000f");
                    }
                });//END OF LAYER BACKGROUND
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="FOREGROUND">
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutVertical();
//                        backgroundColor("#000f");

                        //<editor-fold defaultstate="collapsed" desc="TOP PANEL">
                        panel(new PanelBuilder("panel_top") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("25%");
                                width("75%");
                                image(new ImageBuilder() {
                                    {
                                        filename("Interface/Nifty/owl.png");
                                        width("100%");
                                    }
                                });
                            }
                        });//END OF PANEL TOP
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="MIDDLE PANEL">
                        panel(new PanelBuilder("panel_mid") {
                            {
                                childLayoutVertical();
                                alignCenter();
                                height("50%");
                                width("50%");
                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        text("Welcome to my walking avatar test!");
                                        font("Interface/Fonts/Default.fnt");
                                        height("25%");
//                                        width("25%");
                                    }
                                });
                                image(new ImageBuilder() {
                                    {
                                        filename("Interface/Nifty/avatar.png");
                                        width("60%");
                                        height("60%");
                                        alignCenter();
                                    }
                                });
//                                
//                               
                            }
                        });//END OF PANEL MID
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="BOTTOM PANEL">
                        panel(new PanelBuilder("panel_bottom") {
                            {
                                childLayoutHorizontal();
                                alignCenter();
                                height("25%");
                                width("75%");

                                //<editor-fold defaultstate="collapsed" desc="BOTTOM LEFT PANEL">
                                panel(new PanelBuilder("panel_bottom_left") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
                                        height("50%");
                                        width("50%");

                                        control(new ButtonBuilder("EditAvatarButton", "EDIT AVATAR") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                interactOnClick("startAvatarEditor()");
                                            }
                                        });
                                    }
                                });//END OF PANEL BOTTOM LEFT
                                //</editor-fold>

                                //<editor-fold defaultstate="collapsed" desc="BOTTOM MID PANEL">
                                panel(new PanelBuilder("panel_bottom_mid") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
                                        height("50%");
                                        width("50%");

                                        control(new ButtonBuilder("StartButton", "START") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                interactOnClick("startGame()");
                                            }
                                        });
                                    }
                                });//END OF PANEL BOTTOM MID
                                //</editor-fold>

                            }
                        });//END OF PANEL BOTTOM
                        //</editor-fold>
                    }
                });//END OF LAYER FOREGROUND
                //</editor-fold>

            }
        }.build(nifty));//END OF SCREEN START
        //</editor-fold>

//        
//
//        
        nifty.gotoScreen("MainMenu");
    }
}
