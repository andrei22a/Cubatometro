package upv.dadm.cubatometro.Listeners;

import java.util.List;

import upv.dadm.cubatometro.entidades.Registro;

public interface RegistrosListener {
    void onRegistrosReceived(List<Registro> registros);
    void onError(Throwable error);

}
