package upv.dadm.cubatometro.Listeners;

import java.util.List;

import upv.dadm.cubatometro.entidades.Grupo;

public interface GroupsListener {
    void onGroupsReceived(List<Grupo> grupos);
    void onError(Throwable error);
}
