package infrastructure;

import domain.Account;
import domain.Asset;
import java.util.List;

public interface Repository {
    void saveAccount(Account account);
    Account loadAccount(String id);
    List<Asset> loadAssets();
}
