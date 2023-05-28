package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class NumDescription extends TypeDescription {
    private final CodeFactory factory;

    public NumDescription(CodeFactory factory) {
        super(NumModel.class, "!Num");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("red", boolean.class, null, "setRedefines");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("num", boolean.class, null, "setNumericAccess");
        substituteProperty("norm", NormalizeNumMode.class, null, "setNormalize");
    }
    public Object newInstance(Node node) {
        return factory.newNumModel();
    }
}
