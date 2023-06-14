package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class NuxDfltDescription extends TypeDescription {
    public NuxDfltDescription() {
        super(FieldDefault.NuxDefault.class);
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeNumMode.class, null, "setNormalize");
        substituteProperty("ini", InitializeNuxMode.class, null, "setInitialize");
        substituteProperty("wid", WordWidth.class, null, "setWordWidth");
        substituteProperty("acc", AccesMode.class, null, "setAccess");
    }
}
