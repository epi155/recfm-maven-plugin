package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class FilDfltDescription extends TypeDescription {
    public FilDfltDescription() {
        super(FieldDefault.FilDefault.class);
        substituteProperty("fill", char.class, null, "setFill");
    }
}
