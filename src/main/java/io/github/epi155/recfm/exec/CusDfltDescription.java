package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;

public class CusDfltDescription extends TypeDescription {
    public CusDfltDescription() {
        super(FieldDefault.CusDefault.class);
        substituteProperty("chk", CheckUser.class, null, "setCheck");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeAbcMode.class, null, "setNormalize");
        substituteProperty("get", boolean.class, null, "setCheckGetter");
        substituteProperty("set", boolean.class, null, "setCheckSetter");
    }
}
