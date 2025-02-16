package org.example.back1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    void deleteByUser(User user);
}