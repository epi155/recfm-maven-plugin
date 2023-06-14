package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class NumDescription extends TypeDescription {
    private final CodeFactory factory;

    public NumDescription(CodeFactory factory) {
        super(NumModel.class, "!Num");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("ovr", boolean.class, null, "setOverride");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("nrm", NormalizeNumMode.class, null, "setNormalize");
        substituteProperty("acc", AccesMode.class, null, "setAccess");
        substituteProperty("wid", WordWidth.class, null, "setWordWidth");
    }
    public Object newInstance(Node node) {
        return factory.newNumModel();
    }
}
