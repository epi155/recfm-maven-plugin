package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class FilDfltDescription extends TypeDescription {
    public FilDfltDescription() {
        super(FieldDefault.FilDefault.class);
        substituteProperty("fill", char.class, null, "setFill");
    }
}
