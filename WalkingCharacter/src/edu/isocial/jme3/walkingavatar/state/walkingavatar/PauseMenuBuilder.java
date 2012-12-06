/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.state.walkingavatar;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author justin
 */
public enum PauseMenuBuilder {

    PAUSEMENUBUILDER;

    public static PauseMenuBuilder getInstance() {
        return PAUSEMENUBUILDER;
    }

    public void buildPauseMenu(final Nifty nifty, final ScreenController controller) {


        //<editor-fold defaultstate="collapsed" desc="MAIN MENU">
        nifty.addScreen("PauseMenu", new ScreenBuilder("PauseMenu") {
            {
                controller(controller);
                //<editor-fold defaultstate="collapsed" desc="BACKGROUND">
                layer(new LayerBuilder("background") {
                    {
                        childLayoutVertical();
                     
                        
//                        backgroundColor("#000f");
                    }
                });//END OF LAYER BACKGROUND
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="FOREGROUND">
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutCenter();
//                        backgroundColor("#000f");

                        //<editor-fold defaultstate="collapsed" desc="TOP PANEL">
                        panel(new PanelBuilder("panel_top") {
                            {
                                childLayoutVertical();
                                alignCenter();
                                height("50%");
                                width("50%");
                                backgroundColor("#0000");
                                
                                control(new ButtonBuilder("MainMenuButton", "Main Menu") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("50%");
                                                interactOnClick("goToMainMenu()");
                                            }
                                        });
                                control(new ButtonBuilder("ResumeButton", "Resume") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("50%");
                                                interactOnClick("resumeGame()");
                                            }
                                        });
                                
//                                image(new ImageBuilder() {
//                                    {
//                                        filename("Interface/Nifty/pause.jpeg");
//                                        width("60%");
//                                        height("60%");
//                                        alignCenter();
//                                    }
//                                });
                            }
                        });//END OF PANEL TOP
                        //</editor-fold>
                    }
                });//END OF LAYER FOREGROUND
                //</editor-fold>

            }
        }.build(nifty));//END OF SCREEN START
        //</editor-fold>

        nifty.gotoScreen("PauseMenu");
    }
}
