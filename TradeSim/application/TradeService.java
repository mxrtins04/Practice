package application;

import domain.*;
import infrastructure.Repository;
import java.util.List;

public class TradeService {
    private Repository repository;

    public TradeService(Repository repository) {
        this.repository = repository;
    }

    public void buyAsset(String assetName, double amount) {}
    public void sellAsset(String assetName, double amount) {}
    public void showPortfolio() {}
}
