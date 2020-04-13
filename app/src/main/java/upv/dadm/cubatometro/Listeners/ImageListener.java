package upv.dadm.cubatometro.Listeners;

import android.widget.ImageView;

public interface ImageListener {
    void onImageReceived(String imageURI);
    void onError(Throwable error);
}
