/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.state.login;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author justin
 */
public enum LoginScreenBuilder {

    LOGINBUILDER;

    public static LoginScreenBuilder getInstance() {
        return LOGINBUILDER;
    }

    public void buildLoginScreen(final Nifty nifty, final ScreenController controller) {
        
            
        //<editor-fold defaultstate="collapsed" desc="LOGIN SCREEN">
        nifty.addScreen("LoginScreen", new ScreenBuilder("LoginScreen") {
            {
                controller(controller);
                //<editor-fold defaultstate="collapsed" desc="BACKGROUND">
                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();
                        backgroundColor("#000f");
                    }
                });//END OF LAYER BACKGROUND
                //</editor-fold>
                
                //<editor-fold defaultstate="collapsed" desc="FOREGROUND">
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutVertical();
                        backgroundColor("#0000");
                        
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
                                //<editor-fold defaultstate="collapsed" desc="MID-TOP PANEL">
                                panel(new PanelBuilder("panel_mid_top") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("33%");
                                        width("100%");
                                        
                                        text(new TextBuilder() {
                                            {
                                                text("Username: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("100%");
                                                width("25%");
                                            }
                                        });
                                        control(new TextFieldBuilder("UsernameTextField") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("75%");
                                                
                                            }
                                        });
                                    }
                                });//END OF PANEL MID TOP
                                //</editor-fold>
                                
                                //<editor-fold defaultstate="collapsed" desc="MID-MID PANEL">
                                panel(new PanelBuilder("panel_mid_mid") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("33%");
                                        width("100%");
                                        
                                        text(new TextBuilder() {
                                            {
                                                text("Password: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("100%");
                                                width("25%");
                                            }
                                        });
                                        control(new TextFieldBuilder("PasswordTextField") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("75%");
                                            }
                                        });
                                        
                                    }
                                });//END OF PANEL MID MID
                                //</editor-fold>
                                
                                //<editor-fold defaultstate="collapsed" desc="MID-BOTTOM PANEL">
                                panel(new PanelBuilder("panel_mid_bottom") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("33%");
                                        width("100%");
                                        
                                        text(new TextBuilder() {
                                            {
                                                text("Server: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("100%");
                                                width("25%");
                                            }
                                        });
                                        control(new TextFieldBuilder("ServerTextField") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("75%");
                                                
                                                
                                            }
                                        });
                                    }
                                });//END OF PANEL MID BOTTOM
                                //</editor-fold>
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
                                        
                                        control(new ButtonBuilder("AdvancedButton", "Advanced") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
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
                                        width("25%");
                                        
                                        control(new ButtonBuilder("CancelButton", "Cancel") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                interactOnClick("quitGame()");
                                            }
                                        });
                                    }
                                });//END OF PANEL BOTTOM MID
                                //</editor-fold>
                                
                                //<editor-fold defaultstate="collapsed" desc="BOTTOM RIGHT PANEL">
                                panel(new PanelBuilder("panel_bottom_right") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
                                        height("50%");
                                        width("25%");
                                        
                                        control(new ButtonBuilder("OkButton", "Ok") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                interactOnClick("authenticate()");
                       
                                            }
                                        });
                                    }
                                });//END OF PANEL BOTTOM RIGHT
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
        
        nifty.gotoScreen("LoginScreen");

    }
    
    public void buildOptionsScreen(final Nifty nifty, final ScreenController controller){
        //<editor-fold defaultstate="collapsed" desc="LOGIN OPTIONS SCREEN">
        nifty.addScreen("LoginScreen", new ScreenBuilder("LoginScreen") {
            {
                controller(controller);
                //<editor-fold defaultstate="collapsed" desc="BACKGROUND">
                layer(new LayerBuilder("background") {
                    {
                        childLayoutCenter();
                        backgroundColor("#000f");
                    }
                });//END OF LAYER BACKGROUND
                //</editor-fold>
                
                //<editor-fold defaultstate="collapsed" desc="FOREGROUND">
                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutVertical();
                        backgroundColor("#0000");
                        
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
                                //<editor-fold defaultstate="collapsed" desc="MID-TOP PANEL">
                                panel(new PanelBuilder("panel_mid_top") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("33%");
                                        width("100%");
                                        
                                        text(new TextBuilder() {
                                            {
                                                text("Username: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("100%");
                                                width("25%");
                                            }
                                        });
                                        control(new TextFieldBuilder("UsernameTextField") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("75%");
                                                
                                            }
                                        });
                                    }
                                });//END OF PANEL MID TOP
                                //</editor-fold>
                                
                                //<editor-fold defaultstate="collapsed" desc="MID-MID PANEL">
                                panel(new PanelBuilder("panel_mid_mid") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("33%");
                                        width("100%");
                                        
                                        text(new TextBuilder() {
                                            {
                                                text("Password: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("100%");
                                                width("25%");
                                            }
                                        });
                                        control(new TextFieldBuilder("PasswordTextField") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("75%");
                                            }
                                        });
                                        
                                    }
                                });//END OF PANEL MID MID
                                //</editor-fold>
                                
                                //<editor-fold defaultstate="collapsed" desc="MID-BOTTOM PANEL">
                                panel(new PanelBuilder("panel_mid_bottom") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("33%");
                                        width("100%");
                                        
                                        text(new TextBuilder() {
                                            {
                                                text("Server: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("100%");
                                                width("25%");
                                            }
                                        });
                                        control(new TextFieldBuilder("ServerTextField") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("30%");
                                                width("75%");
                                                
                                                
                                            }
                                        });
                                    }
                                });//END OF PANEL MID BOTTOM
                                //</editor-fold>
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
                                        
                                        control(new ButtonBuilder("AdvancedButton", "Advanced") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
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
                                        width("25%");
                                        
                                        control(new ButtonBuilder("CancelButton", "Cancel") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                interactOnClick("quitGame()");
                                            }
                                        });
                                    }
                                });//END OF PANEL BOTTOM MID
                                //</editor-fold>
                                
                                //<editor-fold defaultstate="collapsed" desc="BOTTOM RIGHT PANEL">
                                panel(new PanelBuilder("panel_bottom_right") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
                                        height("50%");
                                        width("25%");
                                        
                                        control(new ButtonBuilder("OkButton", "Ok") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                height("50%");
                                                width("50%");
                                                interactOnClick("checkTextBoxInfo()");
                       
                                            }
                                        });
                                    }
                                });//END OF PANEL BOTTOM RIGHT
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
    }
}
