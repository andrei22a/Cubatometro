package upv.dadm.cubatometro.Listeners;

import java.util.List;

import upv.dadm.cubatometro.entidades.User;

public interface MembersListener {
    void onMembersReceived(List<User> members);
    void onError(Throwable error);
}
