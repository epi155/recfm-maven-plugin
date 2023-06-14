package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class AbcDfltDescription extends TypeDescription {
    public AbcDfltDescription() {
        super(FieldDefault.AbcDefault.class);
        substituteProperty("pad", char.class, null, "setPadChar");
        substituteProperty("ini", char.class, null, "setInitChar");
        substituteProperty("chk", CheckAbc.class, null, "setCheck");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeAbcMode.class, null, "setNormalize");
        substituteProperty("get", boolean.class, null, "setCheckGetter");
        substituteProperty("set", boolean.class, null, "setCheckSetter");
    }
}
