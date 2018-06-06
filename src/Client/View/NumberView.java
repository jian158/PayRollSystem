package Client.View;

import javafx.scene.control.TextField;

/**
 * Created by wei on 2018/4/10.
 */
/*
* 限制只能输入数字
* */
public class NumberView extends TextField{

    public NumberView() {
        super();
    }

    public NumberView(String text) {
        super(text);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if(text.matches("[0-9]+")||text.length()==0)
            super.replaceText(start, end, text);
    }

    @Override
    public void replaceSelection(String replacement) {
        if(replacement.matches("[0-9]+")||replacement.length()==0)
          super.replaceSelection(replacement);

    }

}
