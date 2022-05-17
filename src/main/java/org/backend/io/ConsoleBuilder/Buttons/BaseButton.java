package org.backend.io.ConsoleBuilder.Buttons;

import lombok.Getter;
import org.backend.Main;
import org.backend.io.ConsoleBuilder.MenuBuilder;

import java.util.function.Function;

public class BaseButton implements Button {
    private Function<Void,Void> activateFunction;
    private Function<Void,Boolean> isActiveFunction;
    @Getter
    private String textButton;
    private boolean isExit;
    @Getter
    private boolean isBack;

    BaseButton(Function<Void, Void> activateFunction,Function<Void,Boolean> isActiveFunction, String textButton, boolean isExit, boolean isBack) {
        this.activateFunction = activateFunction;
        this.isActiveFunction = isActiveFunction;
        this.textButton = textButton;
        this.isExit = isExit;
        this.isBack = isBack;
    }

    public static BaseButtonBuilder builder(MenuBuilder parent) {
        return new BaseButtonBuilder(parent);
    }

    @Override
    public void activate() {
        if(!isActive())
        {
            return;
        }
        if(isExit)
        {
            Main.mainMenu.exit();
            return;
        }
        activateFunction.apply(null);

    }

    @Override
    public boolean isActive() {
        if(isActiveFunction != null)
        {
            return isActiveFunction.apply(null);
        }
        return true;
    }

    public static class BaseButtonBuilder {
        private Function<Void, Void> activateFunction;
        private Function<Void,Boolean> isActiveFunction;
        private String textButton;
        private boolean isExit;
        private boolean isBack;
        private MenuBuilder parent;

        BaseButtonBuilder(MenuBuilder parent) {
            this.parent = parent;
        }

        public BaseButtonBuilder activateFunction(Function<Void, Void> activateFunction) {
            this.activateFunction = activateFunction;
            return this;
        }

        public BaseButtonBuilder textButton(String textButton) {
            this.textButton = textButton;
            return this;
        }

        public BaseButtonBuilder isExit(boolean isExit) {
            this.isExit = isExit;
            return this;
        }

        public BaseButtonBuilder isBack(boolean isBack) {
            this.isBack = isBack;
            return this;
        }

        public BaseButtonBuilder isActiveFunction(Function<Void,Boolean> isActiveFunction) {
            this.isActiveFunction = isActiveFunction;
            return this;
        }

        public MenuBuilder build() {
            parent.addButton(new BaseButton(activateFunction,isActiveFunction, textButton, isExit, isBack));
            return parent;
        }
    }
}
