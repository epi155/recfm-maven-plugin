package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.*;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class ClassDescription extends TypeDescription {
    private final CodeFactory factory;

    public ClassDescription(CodeFactory factory) {
        super(ClassModel.class);
        this.factory = factory;
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("ovf", LoadOverflowAction.class, null, "setOnOverflow");
        substituteProperty("unf", LoadUnderflowAction.class, null, "setOnUnderflow");
    }
    public Object newInstance(Node node) {
        return factory.newClassModel();
    }
}
