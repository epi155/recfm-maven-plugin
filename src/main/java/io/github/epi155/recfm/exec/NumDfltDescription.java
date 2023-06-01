package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class NumDfltDescription extends TypeDescription {
    public NumDfltDescription() {
        super(FieldDefault.NumDefault.class);
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeNumMode.class, null, "setNormalize");
        substituteProperty("wid", WordWidth.class, null, "setWordWidth");
        substituteProperty("acc", AccesMode.class, null, "setAccess");
    }
}
