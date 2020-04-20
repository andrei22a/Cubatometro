package upv.dadm.cubatometro.Listeners;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import upv.dadm.cubatometro.entidades.Registro;

public interface RegistrosListener {
    void onRegistrosReceived(HashMap<String, List<Registro>> registrosGrupo) throws ParseException;
    void onError(Throwable error);

}
