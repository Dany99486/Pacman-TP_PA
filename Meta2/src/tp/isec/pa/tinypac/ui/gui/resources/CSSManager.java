package tp.isec.pa.tinypac.ui.gui.resources;

import javafx.scene.Parent;

public class CSSManager {
    private CSSManager() { }

    public static void applyCSS(Parent parent, String filename) {
        var url = CSSManager.class.getResource("css/"+filename);
        if (url == null)
            return;
        String fileCSS = url.toExternalForm();
        parent.getStylesheets().add(fileCSS);
    }
}

