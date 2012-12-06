/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.state.avatareditor;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author justin
 */
public enum AvatarEditorHUDBuilder {

    HUDBUILDER;

    public static AvatarEditorHUDBuilder getInstance() {
        return HUDBUILDER;
    }

    public void buildHUD(final Nifty nifty, final ScreenController controller) {


        //<editor-fold defaultstate="collapsed" desc="HUD SCREEN">
        nifty.addScreen("AvatarEditor", new ScreenBuilder("AvatarEditor") {
            {
                controller(controller);

                layer(new LayerBuilder("background") {
                    {
                        childLayoutHorizontal();
//            backgroundColor("#000f");
                        // <!-- ... -->

                        panel(new PanelBuilder("background_left") {
                            {
                                childLayoutVertical();
//                backgroundColor("#0f08");
                                height("100%");
                                width("70%");

                                image(new ImageBuilder() {
                                    {
                                        filename("Textures/hudFrame.png");
                                        height("100%");
                                        width("100%");
                                    }
                                });
                            }
                        });
                        panel(new PanelBuilder("background_right") {
                            {
                                childLayoutVertical();
//                backgroundColor("#0f08");
                                height("100%");
                                width("30%");
                                image(new ImageBuilder() {
                                    {
                                        filename("Textures/hudFrame.png");
                                        height("100%");
                                        width("100%");
                                    }
                                });
                            }
                        });

                    }
                });

                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutHorizontal();
//            backgroundColor("#0000");

                        // panel added
                        panel(new PanelBuilder("panel_left") {
                            {
                                childLayoutVertical();
//                backgroundColor("#0f08");
                                height("100%");
                                width("70%");
                                // <!-- spacer -->
                            }
                        });

                        panel(new PanelBuilder("panel_right") {
                            {

                                childLayoutVertical();
//                backgroundColor("#00f8");
                                height("100%");
                                width("30%");



                                panel(new PanelBuilder("panel_right_top") {
                                    {
                                        childLayoutVertical();
//                    backgroundColor("#00f8");
                                        height("30%");
                                        width("100%");


                                    }
                                });
                                panel(new PanelBuilder("panel_right_pants") {
                                    {
                                        childLayoutVertical();
//                                        backgroundColor("#000f");
                                        height("20%");
                                        width("100%");

                                        text(new TextBuilder() {
                                            {
                                                text("Legs:");
                                                font("Interface/Fonts/Default.fnt");
                                                height("50%");
                                                width("50%");
                                                align(Align.Center);
                                            }
                                        });

                                        control(new DropDownBuilder("DropDownLegs") {
                                            {
                                                valignCenter();
                                                alignCenter();
                                                width("50%");
                                            }
                                        });
                                    }
                                });

                                panel(new PanelBuilder("panel_right_torso") {
                                    {
                                        childLayoutVertical();
//                                        backgroundColor("#000f");
                                        height("20%");
                                        width("100%");

                                        text(new TextBuilder() {
                                            {
                                                text("Torso:");
                                                font("Interface/Fonts/Default.fnt");
                                                height("33%");
                                                width("25%");
                                                align(Align.Center);
                                            }
                                        });
                                        panel(new PanelBuilder("panel_right_torso_torso") {
                                            {
                                                childLayoutVertical();
//                                        backgroundColor("#000f");
                                                height("33%");
                                                width("100%");
                                                control(new DropDownBuilder("DropDownTorso") {
                                                    {
                                                        valignCenter();
                                                        alignCenter();
                                                        width("50%");
                                                    }
                                                });



                                            }
                                        });
//                                        panel(new PanelBuilder("panel_right_torso_color") {
//                                            {
//                                                childLayoutVertical();
////                                        backgroundColor("#000f");
//                                                height("33%");
//                                                width("100%");
//                                                control(
//                                                        new DropDownBuilder("DropDownColor") {
//                                                            {
//                                                                valignCenter();
//                                                                alignCenter();
//                                                                width("50%");
//                                                            }
//                                                        });
//
//                                            }
//                                        });
                                    }
                                });

                                panel(new PanelBuilder("panel_right_changebutton") {
                                            {
                                                childLayoutVertical();
//                                                valignCenter();
//                                                alignCenter();
//                                        backgroundColor("#00f8");
                                                height("10%");
                                                width("100%");
                                                control(new ButtonBuilder("changeButton", "Change") {
                                                    {
                                                        alignCenter();
                                                        valignCenter();
                                                        height("50%");
                                                        width("50%");
                                                        interactOnClick("putOnClothingItem()");
                                                    }
                                                });
                                                

                                            }
                                        });
                                panel(new PanelBuilder("panel_right_mainmenubutton") {
                                            {
                                                childLayoutVertical();
//                                                valignCenter();
//                                                alignCenter();
//                                        backgroundColor("#00f8");
                                                height("10%");
                                                width("100%");
                                                
                                                control(new ButtonBuilder("mainMenuButton", "Main Menu") {
                                                    {
                                                        alignCenter();
                                                        valignCenter();
                                                        height("50%");
                                                        width("50%");
                                                        interactOnClick("goBackToMainMenu()");
                                                    }
                                                });

                                            }
                                        });
                            }
                        }); // panel added
                    }
                });
            }
        }.build(nifty));
        //</editor-fold>
        nifty.gotoScreen("AvatarEditor");
    }
}
