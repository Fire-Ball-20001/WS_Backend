package org.console.io.ConsoleBuilder;

import lombok.Getter;
import org.backend.Main;
import org.console.io.ConsoleBuilder.Buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class BaseMenu implements Menu {
    private List<Menu> menus;
    private List<Button> buttons;
    private boolean isExit = false;
    private int this_menu = -1;
    @Getter
    private String text;

    public BaseMenu(List<Button> buttons, List<Menu> menus,String text) {
        this.menus = menus;
        this.buttons = buttons;
        this.text = text;
    }

    @Override
    public void showMenu() {
        while(true)
        {
            if(isExit)
            {
                break;
            }

            List<Button> activeButtons = getActiveButtons();
            Main.output.textListCommands(null);
            int count_commands = menus.size()+ activeButtons.size();
            int i = 1;
            for (Menu menu:
                 menus) {
                Main.output.outputTextNL(i + ". " + menu.getText()+";");
                i++;
            }
            for(Button button : activeButtons)
            {
                Main.output.outputTextNL(i + ". " + button.getTextButton()+";");
                i++;
            }

            int command = Main.input.common.enterCommand(count_commands) - 1;
            if(command>=menus.size())
            {
                if(activeButtons.get(command - menus.size()).isBack())
                {
                    break;
                }
                else {
                    activeButtons.get(command - menus.size()).activate();
                }
            }
            else
            {
                menus.get(command).showMenu();
            }
        }
    }

    @Override
    public boolean back() {
        if(this_menu != -1)
        {
            if(menus.get(this_menu).back())
            {
                this_menu = -1;
            }
            return false;
        }

        return true;
    }

    @Override
    public void exit() {
        isExit = true;
    }

    private List<Button> getActiveButtons()
    {
        List<Button> result = new ArrayList<>();
        for(Button button : buttons)
        {
            if(button.isActive())
            {
                result.add(button);
            }
        }
        return result;
    }



}
