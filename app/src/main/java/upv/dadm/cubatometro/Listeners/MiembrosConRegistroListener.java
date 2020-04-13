package upv.dadm.cubatometro.Listeners;

import java.util.List;

import upv.dadm.cubatometro.entidades.User;

public interface MiembrosConRegistroListener {
    void onMiembrosReceived(List<User> miembros);
    void onError(Throwable error);
}
