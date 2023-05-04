package Game.Records;

import java.util.List;

public record LoggerResult(int steps, int lives, List<String>[][] maze) {
}
