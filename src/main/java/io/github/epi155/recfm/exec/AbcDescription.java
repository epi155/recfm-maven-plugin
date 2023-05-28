package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class AbcDescription extends TypeDescription {
    private final CodeFactory factory;

    public AbcDescription(CodeFactory factory) {
        super(AbcModel.class, "!Abc");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("pad", Character.class, null, "setPadChar");
        substituteProperty("chk", CheckChar.class, null, "setCheck");
        substituteProperty("red", boolean.class, null, "setRedefines");
        substituteProperty("ovf", OverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", UnderflowAction.class, null, "setOnUnderflow");
        substituteProperty("norm", NormalizeAbcMode.class, null, "setNormalize");
    }
    public Object newInstance(Node node) {
        return factory.newAbcModel();
    }
}
