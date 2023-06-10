package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class CusDescription extends TypeDescription {
    private final CodeFactory factory;

    public CusDescription(CodeFactory factory) {
        super(CusModel.class, "!Cus");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("chk", CheckCus.class, null, "setCheck");
        substituteProperty("ini", Character.class, null, "setInitChar");
        substituteProperty("pad", Character.class, null, "setPadChar");
        substituteProperty("ovr", boolean.class, null, "setOverride");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeAbcMode.class, null, "setNormalize");
        substituteProperty("get", Boolean.class, null, "setCheckGetter");
        substituteProperty("set", Boolean.class, null, "setCheckSetter");
    }
    public Object newInstance(Node node) {
        return factory.newCusModel();
    }
}
