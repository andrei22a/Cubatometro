package upv.dadm.cubatometro.Listeners;

import java.util.List;

public interface MembersIDListener {
    void onMembersIDReceived(List<String> membersID);
    void onError(Throwable error);
}
