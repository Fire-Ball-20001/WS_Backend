package org.console.io.ConsoleBuilder;

import org.console.io.ConsoleBuilder.Buttons.BaseButton;
import org.console.io.ConsoleBuilder.Buttons.Button;

import java.util.ArrayList;
import java.util.List;


public class MenuBuilder {
    private MenuBuilder parent;
    private List<Menu> menus;
    private List<Button> buttons;
    private String text ="";
    private MenuBuilder(MenuBuilder parent)
    {
        this.parent = parent;
        menus = new ArrayList<>();
        buttons = new ArrayList<>();
    }
    public static MenuBuilder createMenu()
    {
        return new MenuBuilder(null);
    }
    public BaseButton.BaseButtonBuilder addButton()
    {
        return BaseButton.builder(this);
    }
    public MenuBuilder addButton(Button button)
    {
        buttons.add(button);
        return this;
    }
    public MenuBuilder addMenu()
    {
        return new MenuBuilder(this);
    }
    public MenuBuilder addMenu(Menu menu)
    {
        menus.add(menu);
        return this;
    }
    public MenuBuilder setText(String text)
    {
        this.text = text;
        return this;
    }
    public MenuBuilder build()
    {
        Menu menu= new BaseMenu(buttons,menus,text);
        if(parent != null)
        {
            parent.addMenu(menu);
            return parent;
        }
        return null;

    }

    public Menu buildMenu()
    {
        return new BaseMenu(buttons,menus,text);
    }


}
