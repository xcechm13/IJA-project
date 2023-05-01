package Game.Records;

import Game.Fields.PathField;

public record DLSState(PathField field, PathField parent, int deep) {
}
