package tech.ian.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ian.user.entity.Stocks;

import java.util.Optional;

public interface StocksRepository extends JpaRepository<Stocks, Long> {

    Optional<Stocks> findByStockId(String stockId);
}
