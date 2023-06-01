package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class AbcDfltDescription extends TypeDescription {
    public AbcDfltDescription() {
        super(FieldDefault.AbcDefault.class);
        substituteProperty("pad", char.class, null, "setPadChar");
        substituteProperty("ini", char.class, null, "setInitChar");
        substituteProperty("chk", CheckChar.class, null, "setCheck");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeAbcMode.class, null, "setNormalize");
        substituteProperty("get", boolean.class, null, "setCheckGetter");
        substituteProperty("set", boolean.class, null, "setCheckSetter");
    }
}
