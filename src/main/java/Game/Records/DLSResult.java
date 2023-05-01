package Game.Records;

import java.util.List;

public record DLSResult(boolean NotExpandedBeacuseOfDeep, boolean success, List<Point> path) {
}
