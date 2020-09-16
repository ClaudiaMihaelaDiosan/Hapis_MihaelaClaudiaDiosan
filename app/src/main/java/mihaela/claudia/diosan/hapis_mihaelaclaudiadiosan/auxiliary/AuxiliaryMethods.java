package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.auxiliary;

import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.ActionBar;
import java.util.Objects;

public class AuxiliaryMethods {

    public static void makeActivityFullScreen(Window window, ActionBar actionBar) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(actionBar).hide();
    }


}
