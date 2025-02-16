package org.example.back1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.List;

@Service
@SessionScope
public class PointService {
    @Autowired
    private PointRepository pointRepository;

    public List<Point> clear() {
        pointRepository.deleteAll();
        return pointRepository.findAll();
    }

    public List<Point> getPointList() {
        return pointRepository.findAll();
    }
}