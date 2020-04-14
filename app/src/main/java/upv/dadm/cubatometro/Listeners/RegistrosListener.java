package upv.dadm.cubatometro.Listeners;

import java.util.HashMap;
import java.util.List;

import upv.dadm.cubatometro.entidades.Registro;

public interface RegistrosListener {
    void onRegistrosReceived(HashMap<String, List<Registro>> registrosGrupo);
    void onError(Throwable error);

}
